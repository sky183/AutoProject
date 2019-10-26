package com.sb.auto.security;

import com.sb.auto.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    CustomJpaRepository customJpaRepository;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        UserEntity userEntity = customJpaRepository.findByUserId(userId);
        UserEntity userEntity = memberMapper.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException(userId);
        }

        return new UserAccount(userEntity);
    }

    public UserEntity insertUser(UserEntity userEntity) {
        userEntity.encodePassword(passwordEncoder);
//        return this.customJpaRepository.save(userEntity);
        this.memberMapper.save(userEntity);
        return userEntity;
    }
}
