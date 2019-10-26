package com.sb.auto.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Principal 객체
 */
public class UserAccount extends User {

    private UserEntity userEntity;

    public UserAccount(UserEntity userEntity) {
        super(userEntity.getUserId(), userEntity.getUserPassword(), authorities(userEntity));
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    private static Collection<? extends GrantedAuthority> authorities(UserEntity userEntity) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userEntity.getUserRole()));
        return authorities;
    }

}
