package com.sb.auto.account;

import org.springframework.beans.factory.annotation.Autowired;
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
    public AccountEntity createAccount(@ModelAttribute AccountEntity accountEntity) {
        return accountService.createNew(accountEntity);
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
        model.addAttribute("accountEntity", new AccountEntity());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute AccountEntity accountEntity) {
        accountEntity.setRole("USER");
        accountService.createNew(accountEntity);
        return "redirect:/";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        return "access-denied";
    }

}
