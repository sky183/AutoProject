package com.sb.auto.controller;

import com.sb.auto.common.annotation.CurrentUser;
import com.sb.auto.common.util.SecurityLogger;
import com.sb.auto.config.security.User;
import com.sb.auto.mapper.JpaRepository;
import com.sb.auto.model.StockEntity;
import com.sb.auto.model.UserEntity;
import com.sb.auto.service.SampleService;
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

    SampleService sampleService;

    JpaRepository jpaRepository;

    UserDetailService userDetailService;

    @Autowired
    public MainController(SampleService sampleService, JpaRepository jpaRepository, UserDetailService userDetailService) {
        this.sampleService = sampleService;
        this.jpaRepository = jpaRepository;
        this.userDetailService = userDetailService;
    }

    @GetMapping("/pimang")
    public String pimang(Model model) {
        model.addAttribute("stockEntity", new StockEntity());
        return "pimangUp";
    }

    @GetMapping("/pimang/select")
    public String pimangSelect(Model model) {
        model.addAttribute("stockEntity", new StockEntity());
        return "pimangSel";
    }

    @GetMapping("/")
    public String index(Model model, @CurrentUser UserEntity userEntity) {
        if (userEntity == null) {
            model.addAttribute("message", "메인 페이지입니다.");
        } else {
            model.addAttribute("message", userEntity.getUserName() + "님 안녕하세요.");
        }
        return "index";
    }
    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        User user = (User) userDetailService.loadUserByUsername(principal.getName());
        UserEntity userEntity = user.getUserEntity();
        model.addAttribute("userEntity", userEntity);
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
        sampleService.dashboard();
        return "dashboard";
    }
    @GetMapping("/paypal")
    public String paypal(Model model, @CurrentUser UserEntity userEntity) {
        model.addAttribute("userEntity", userEntity);
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
        sampleService.asyncService();
        SecurityLogger.log("MVC, after async service");
        return "Async Service";
    }

}
