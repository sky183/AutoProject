package com.sb.auto.service;

import com.sb.auto.mapper.JpaRepository;
import com.sb.auto.mapper.MemberMapper;
import com.sb.auto.model.UserEntity;
import com.sb.auto.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    JpaRepository jpaRepository;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Principal 객체 반환
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        UserEntity userEntity = jpaRepository.findByUserId(userId);
        UserEntity userEntity = memberMapper.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException(userId);
        }
        return new User(userEntity);
    }

    public UserEntity insertUser(UserEntity userEntity) {
        userEntity.encodePassword(passwordEncoder);
//        return this.jpaRepository.save(userEntity);
        this.memberMapper.save(userEntity);
        return userEntity;
    }
}
