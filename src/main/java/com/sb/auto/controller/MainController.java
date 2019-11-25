package com.sb.auto.controller;

import com.sb.auto.common.annotation.CurrentUser;
import com.sb.auto.common.util.SecurityLogger;
import com.sb.auto.config.security.User;
import com.sb.auto.mapper.JpaRepository;
import com.sb.auto.model.StockVO;
import com.sb.auto.model.UserVO;
import com.sb.auto.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.concurrent.Callable;

@Controller
public class MainController {

    JpaRepository jpaRepository;

    UserDetailService userDetailService;

    @Autowired
    public MainController(JpaRepository jpaRepository, UserDetailService userDetailService) {
        this.jpaRepository = jpaRepository;
        this.userDetailService = userDetailService;
    }

    @GetMapping("/pimang")
    public String pimang(Model model) {
        model.addAttribute("stockVO", new StockVO());
        return "pimangUp";
    }

    @GetMapping("/pimang/select")
    public String pimangSelect(Model model) {
        model.addAttribute("stockVO", new StockVO());
        return "pimangSel";
    }

    @GetMapping("/")
    public String index(Model model, @CurrentUser UserVO userVO) {
        if (userVO == null) {
            model.addAttribute("message", "메인 페이지입니다.");
        } else {
            model.addAttribute("message", userVO.getUserName() + "님 안녕하세요.");
        }
        return "index";
    }
    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        User user = (User) userDetailService.loadUserByUsername(principal.getName());
        UserVO userVO = user.getUserVO();
        model.addAttribute("userVO", userVO);
        return "user";
    }
    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "Hello Admin, " + principal.getName());
        return "admin";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("message", "Hello " + principal.getName());
        userDetailService.dashboard();
        return "dashboard";
    }
    @GetMapping("/paypal")
    public String paypal(Model model, @CurrentUser UserVO userVO) {
        model.addAttribute("userVO", userVO);
        return "paypal";
    }

    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler() {
        SecurityLogger.log("MVC");
        return () -> {
            SecurityLogger.log("Callable");
            return "Async Handler";
        };
    }

    @GetMapping("/async-service")
    @ResponseBody
    public String asyncService() {
        SecurityLogger.log("MVC, before async service");
        userDetailService.asyncService();
        SecurityLogger.log("MVC, after async service");
        return "Async Service";
    }

}
