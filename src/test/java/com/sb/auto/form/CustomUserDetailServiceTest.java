package com.sb.auto.form;

import com.sb.auto.model.UserEntity;
import com.sb.auto.service.CustomUserDetailService;
import com.sb.auto.service.SampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomUserDetailServiceTest {

    @Autowired
    SampleService sampleService;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    public void loadUserByUsername() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserRole("ADMIN");
        userEntity.setUserId("sky183");
        userEntity.setUserPassword("1");
        userEntity.setPhone("010");
        userEntity.setPoint(20000);
        userEntity.setUserName("김성범");
        userEntity.setUserSeq(1);

        customUserDetailService.insertUser(userEntity);

        UserDetails userDetails = customUserDetailService.loadUserByUsername("sky183");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "123");
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        sampleService.dashboard();

    }


}
