package com.sb.auto.account;

import com.sb.auto.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/account/{role}/{username}/{password}")
    @ResponseBody
    public UserEntity createAccount(@ModelAttribute UserEntity userEntity) {
        return accountService.insertUser(userEntity);
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
    public String processSignUp(@ModelAttribute UserEntity userEntity) {
        userEntity.setUserRole("USER");
        accountService.insertUser(userEntity);
//        return "redirect:/";
        return accountService.insertUser(userEntity).toString();
    }

    @GetMapping("/access-denied")
    public String accessDenied(Principal principal, @CurrentUser UserEntity userEntity, Model model) {
        model.addAttribute("userId", principal.getName());
        model.addAttribute("userRole", userEntity.getUserRole());
        return "access-denied";
    }

}
