package com.sb.auto.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Iterator;

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
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)principal;
        model.addAttribute("name", principal.getName());
        model.addAttribute("role", ((UserAccount)token.getPrincipal()).getAccountEntity().getRole());
        return "access-denied";
    }

}
