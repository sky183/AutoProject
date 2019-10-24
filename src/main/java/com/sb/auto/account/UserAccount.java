package com.sb.auto.account;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;

public class UserAccount extends User {

    private Account account;

    public UserAccount(Account account) {
        super(account.getUsername(), account.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_" + account.getRole())));
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
