package com.sb.auto.account;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;

public class UserAccount extends User {

    private AccountEntity accountEntity;

    public UserAccount(AccountEntity accountEntity) {
        super(accountEntity.getUsername(), accountEntity.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_" + accountEntity.getRole())));
        this.accountEntity = accountEntity;
        AccountContext.setAccount(accountEntity);
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }
}
