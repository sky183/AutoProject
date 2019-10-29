package com.sb.auto.controller;

import com.sb.auto.common.annotation.CurrentUser;
import com.sb.auto.model.UserEntity;
import com.sb.auto.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class AccountController {

    UserDetailService userDetailService;

    @Autowired
    public AccountController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping("/account/{userRole}/{userId}/{userPw}")
    @ResponseBody
    public UserEntity createAccount(@ModelAttribute UserEntity userEntity) {
        return userDetailService.insertUser(userEntity);
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutForm() {
        return "logout";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("userEntity", new UserEntity());
        return "signup";
    }

    @PostMapping("/signup")
    @ResponseBody
    public UserEntity processSignUp(@ModelAttribute UserEntity userEntity) {
        userEntity.setUserRole("USER");
//        return "redirect:/";
        return userDetailService.insertUser(userEntity);
    }

    @GetMapping("/access-denied")
    public String accessDenied(Principal principal, @CurrentUser UserEntity userEntity, Model model) {
        model.addAttribute("userId", principal.getName());
        model.addAttribute("userRole", userEntity.getUserRole());
        return "access-denied";
    }

}
