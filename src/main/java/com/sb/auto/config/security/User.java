package com.sb.auto.config.security;

import com.sb.auto.model.UserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Principal 객체 - UserVO를 생성해준다.
 */
public class User extends org.springframework.security.core.userdetails.User {

    private UserVO userVO;

    public User(UserVO userVO) {
        super(userVO.getUserId(), userVO.getUserPw(), authorities(userVO));
        //보안을 위해 password null처리
        userVO.setUserPw(null);
        this.userVO = userVO;
    }

    public UserVO getUserVO() {
        return userVO;
    }

    private static Collection<? extends GrantedAuthority> authorities(UserVO userVO) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userVO.getUserRole()));
        return authorities;
    }

}
