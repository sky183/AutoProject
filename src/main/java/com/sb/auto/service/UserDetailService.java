package com.sb.auto.service;

import com.sb.auto.config.security.User;
import com.sb.auto.mapper.JpaRepository;
import com.sb.auto.mapper.MemberMapper;
import com.sb.auto.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements org.springframework.security.core.userdetails.UserDetailsService {

    JpaRepository jpaRepository;

    MemberMapper memberMapper;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailService(JpaRepository jpaRepository, MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.jpaRepository = jpaRepository;
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Principal 객체 반환 - 메서드에서 주입받는 Principal 은 여기서 반환하는 객체이다.
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity userEntity = memberMapper.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException(userId);
        }
        return new User(userEntity);
    }

    public UserEntity insertUser(UserEntity userEntity) {
        userEntity.encodePassword(passwordEncoder);
        this.memberMapper.save(userEntity);
        return userEntity;
    }

}
