package com.sb.auto.security;

import com.sb.auto.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Principal 객체
 */
public class User extends org.springframework.security.core.userdetails.User {

    private UserEntity userEntity;

    public User(UserEntity userEntity) {
        super(userEntity.getUserId(), userEntity.getUserPw(), authorities(userEntity));
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
