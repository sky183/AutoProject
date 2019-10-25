package com.sb.auto.account;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;

public class UserAccount extends User {

    private UserEntity userEntity;

    public UserAccount(UserEntity userEntity) {
        super(userEntity.getUserId(), userEntity.getUserPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_" + userEntity.getUserRole())));
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }
}
