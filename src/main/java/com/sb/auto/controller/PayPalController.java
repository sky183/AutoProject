package com.sb.auto.controller;

import com.sb.auto.common.util.CollectionsUtil;
import com.sb.auto.common.util.RequestUtil;
import com.sb.auto.config.security.UserChangeFactory;
import com.sb.auto.model.PayPalVO;
import com.sb.auto.model.UserVO;
import com.sb.auto.service.PayPalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class PayPalController {

    PayPalService payPalService;
    UserChangeFactory userChangeFactory;

    public PayPalController(PayPalService payPalService, UserChangeFactory userChangeFactory) {
        this.payPalService = payPalService;
        this.userChangeFactory = userChangeFactory;
    }

    // PDT 전용
    private static final String PARAM_TX = "tx"; //PDT전용
    private static final String PARAM_AT = "at"; //PDT전용
    //페이팔사이트에서 나와있는 Identity token값 - PDT전용
    private static String PARAM_AT_VALUE = "XRiSW0KBgmMT6NC2mGNQUxiQzS6J-kJlYAl07A3nRwxFFVQBmNa3mU8yHh4";
    //공통
    private static final String PARAM_CMD = "cmd";
    private static final String PARAM_ITEM_NAME = "item_name";    // 상품이름
    private static final String PARAM_ITEM_NUMBER = "item_number";   // 상품번호
    private static final String PARAM_PAYMENT_STATUS = "payment_status";       // 결제 상태
    private static final String PARAM_MC_GROSS = "mc_gross";    // 페이팔 결제금액
    private static final String PARAM_MC_FEE = "mc_fee";     // 페이팔 수수료금액
    private static final String PARAM_MC_CURRENCY = "mc_currency";   // 화폐
    private static final String PARAM_TXN_ID = "txn_id";     // 거래번호
    private static final String PARAM_RECEIVER_EMAIL = "receiver_email";       // 페이팔 판매자계정 이메일
    private static final String PARAM_PAYER_EMAIL = "payer_email";   // 페이팔 구매자계정 이메일
    private static final String PARAM_CUSTOM = "custom";     // 상점회원번호

    /**
     * 페이팔 결제 PDT정보 핸들링
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/paypal/pdt")
    public String handleRequestPDT(Model model) throws Exception {
        String requestFlag = "PDT";
        UserVO userVO = payPalListener(requestFlag);
        if (userVO != null ) model.addAttribute("userVO", userVO);
        return "user";
    }

    /**
     * 페이팔 결제 IPN정보 핸들링
     * @throws Exception
     */
    @PostMapping("/paypal/ipn")
    @ResponseStatus(HttpStatus.OK)
    public void handleRequestIPNPost() throws Exception {
        String requestFlag = "IPN";
        payPalListener(requestFlag);
    }


    public UserVO payPalListener(String requestFlag) throws Exception {

        String urlPaypalValidate;
        String paramCmdValue;
        String responseSuccess;
        String responseFail;

        if (requestFlag.equals("PDT")) {
            //테스트용 주소, 실제 결제시 주석을 풀고 아래것으로 바꾼다.
            urlPaypalValidate = "https://www.sandbox.paypal.com/cgi-bin/webscr";
            //urlPaypalValidate = "https://www.paypal.com/cgi-bin/webscr";
            paramCmdValue = "_notify-synch";
            responseSuccess = "SUCCESS";
            responseFail = "FAIL";
        } else {
            //테스트용 주소, 실제 결제시 주석을 풀고 아래것으로 바꾼다.
            urlPaypalValidate = "https://ipnpb.sandbox.paypal.com/cgi-bin/webscr";
            //urlPaypalValidate = "https://ipnpb.paypal.com/cgi-bin/webscr";
            paramCmdValue = "_notify-validate";
            responseSuccess = "VERIFIED";
            responseFail = "INVALID";
        }

        // PayPal로부터온 파라미터를 다시 PayPal로 게시하기 위해 파라미터를 구성한다.
        Enumeration en = RequestUtil.getParameterNames();
        String str = PARAM_CMD + "=" + paramCmdValue;
        HashMap vars = new HashMap();
        Map map = new HashMap();
        while (en.hasMoreElements()) {
            String paramName = (String) en.nextElement();
            String paramValue = RequestUtil.getParameter(paramName);
            str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue, "UTF-8");
            if(requestFlag.equals("IPN")) vars.put(paramName, URLEncoder.encode(paramValue, "UTF-8"));
        }
        if(requestFlag.equals("PDT")) str = str + "&" + PARAM_AT + "=" + PARAM_AT_VALUE;

        // 유효성을 검사하기 위해 PayPal로 다시 전송시작.
        URL u = new URL(urlPaypalValidate);
        HttpURLConnection uc = (HttpURLConnection) u.openConnection();
        uc.setDoOutput(true);
        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        PrintWriter pw = new PrintWriter(uc.getOutputStream());
        pw.println(str);
        pw.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String res = in.readLine();

        if (res.equals(responseSuccess)) {
            log.info("페이팔서버로 부터 "+ requestFlag +"유효성 요청이 성공했습니다.");
            if(requestFlag.equals("PDT")) {
                String[] temp;
                while ((res = in.readLine()) != null) {
                    temp = res.split("=");
                    if (temp.length == 2) {
                        vars.put(temp[0], URLDecoder.decode(temp[1], "UTF-8"));
                        log.info("{}{}{}", new Object[]{temp[0], ":", temp[1]});
                    } else {
                        vars.put(temp[0], "");
                        log.info("{}{}{}", new Object[]{temp[0], ":", " "});
                    }

                }
            }

            PayPalVO payPalVO = new PayPalVO();
            CollectionsUtil.mapToBean(vars, payPalVO);

            log.info("paypalVO : " + payPalVO);

            //DB 작업 및 응답페이지 호출 등등 작업을 한다
            if (payPalVO.getPaymentStatus().equals("Completed")) {
                payPalService.updatePoint(payPalVO);
            }
            if(requestFlag.equals("PDT")) {
                return userChangeFactory.newUserVO(payPalVO.getCustom());
            }
        } else if (res.equals(responseFail)) {
            log.warn("페이팔서버로 부터 "+ requestFlag +"유효성 요청이 실패했습니다. 상태:" + res);
        } else {
            log.error("페이팔서버로 부터 "+ requestFlag +"유효성 요청이 실패했습니다. 상태:" + res);
        }
        return null;
    }


}


