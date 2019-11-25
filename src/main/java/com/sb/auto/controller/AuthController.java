package com.sb.auto.controller;

import com.sb.auto.common.annotation.CurrentUser;
import com.sb.auto.model.UserVO;
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
public class AuthController {

    UserDetailService userDetailService;

    @Autowired
    public AuthController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
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
        model.addAttribute("userVO", new UserVO());
        return "signup";
    }

    @PostMapping("/signup")
    @ResponseBody
    public UserVO processSignUp(@ModelAttribute UserVO userVO) {
        userVO.setUserRole("USER");
//        return "redirect:/";
        return userDetailService.insertUser(userVO);
    }

    @GetMapping("/access-denied")
    public String accessDenied(Principal principal, @CurrentUser UserVO userVO, Model model) {
        model.addAttribute("userId", principal.getName());
        model.addAttribute("userRole", userVO.getUserRole());
        return "access-denied";
    }

}
