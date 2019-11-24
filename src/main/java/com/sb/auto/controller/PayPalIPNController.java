package com.sb.auto.controller;

import com.sb.auto.service.PayPalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
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
public class PayPalIPNController {

    PayPalService payPalService;

    public PayPalIPNController(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    private static String URL_PAYPAL_VALIDATE; // IPN데이터를 페이팔로 보낼 서버주소

    // IPN 첫번째 응답 변수 선언
//    private static final String PARAM_TX = "tx"; //PDT전용
    private static final String PARAM_CMD = "cmd";
    private static final String PARAM_CMD_VALUE = "_notify-validate";
    private static final String RESPONSE_SUCCESS = "VERIFIED";
    private static final String RESPONSE_FAIL = "INVALID";

    static {
        //테스트용 주소, 실제 결제시 주석을 풀고 아래것으로 바꾼다.
        URL_PAYPAL_VALIDATE = "https://ipnpb.sandbox.paypal.com/cgi-bin/webscr";
        //URL_PAYPAL_VALIDATE = "https://ipnpb.paypal.com/cgi-bin/webscr";
    }

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
     * 페이팔 결제 IPN정보 핸들링
     * @param request
     * @throws Exception
     */
    @PostMapping("/paypal/ipn")
    @ResponseStatus(HttpStatus.OK)
    public void handleRequestIPNPost(HttpServletRequest request/*, Model model*/) throws Exception {

        // PayPal로부터온 파라미터를 그대로 다시 String으로 표시한다.
        Enumeration en = request.getParameterNames();
        String readString = "";
        while (en.hasMoreElements()) {
            String paramName = (String) en.nextElement();
            String paramValue = request.getParameter(paramName);
            readString = readString + "&" + paramName + "=" + URLDecoder.decode(paramValue, "UTF-8");
        }
        log.info("Received IPN from PayPal:" + readString);

        // 다시 PayPal로 게시하기 위해 파라미터를 구성한다.
        String str = PARAM_CMD + "=" + PARAM_CMD_VALUE;
        en = request.getParameterNames();
        HashMap vars = new HashMap();
        Map map = new HashMap();
        while (en.hasMoreElements()) {
            String paramName = (String) en.nextElement();
            String paramValue = request.getParameter(paramName);
            str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue, "UTF-8");
            vars.put(paramName, URLEncoder.encode(paramValue, "UTF-8"));
        }

        String paymentStatus = (String) vars.get(PARAM_PAYMENT_STATUS);
        double paymentAmount = Double.parseDouble((String) vars.get(PARAM_MC_GROSS));
        double paymentFee = Double.parseDouble((String) vars.get(PARAM_MC_FEE));
        String txnId = (String) vars.get(PARAM_TXN_ID);
        String userseq = (String) vars.get(PARAM_CUSTOM);

        map.put("paymentStatus", paymentStatus);
        map.put("paymentAmount", paymentAmount);
        map.put("paymentFee", paymentFee);
        map.put("txnId", txnId);
        map.put("userseq", userseq);

        log.info("res:"+map);

        log.info("Sending IPN to PayPal:" + str);

        // 유효성을 검사하기 위해 PayPal로 다시 전송시작.
        URL u = new URL(URL_PAYPAL_VALIDATE);
        HttpURLConnection uc = (HttpURLConnection) u.openConnection();
        uc.setDoOutput(true);
        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        PrintWriter pw = new PrintWriter(uc.getOutputStream());
        pw.println(str);
        pw.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String res = in.readLine();

        if (res.equals(RESPONSE_SUCCESS)) {
            log.info("페이팔서버로 부터 IPN유효성 요청이 성공했습니다.");
            //DB 작업 및 응답페이지 호출 등등 작업을 한다
            if (paymentStatus.equals("Completed")) {
                Map paymap = new HashMap();
                paymap.put("txnId", txnId);
                paymap.put("userId", userseq);
                paymap.put("paymentAmount", paymentAmount);
                paymap.put("paymentFee", paymentFee);
                payPalService.updatePoint(paymap);
            }
        } else if (res.equals(RESPONSE_FAIL)) {
//            model.addAttribute("request", res);
            log.warn("페이팔서버로 부터 IPN유효성 요청이 실패했습니다. 상태:" + res);
        } else {
//            model.addAttribute("request", res);
            log.error("페이팔서버로 부터 IPN유효성 요청이 실패했습니다. 상태:" + res);
        }
    }


}


