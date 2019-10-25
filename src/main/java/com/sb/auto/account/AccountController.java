package com.sb.auto.account;

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
        return "redirect:/";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Principal principal, @AuthenticationPrincipal UserAccount userAccount, Model model) {
        model.addAttribute("userId", principal.getName());
        model.addAttribute("userRole", userAccount.getUserEntity().getUserRole());
        return "access-denied";
    }

}
