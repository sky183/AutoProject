package com.sb.auto.form;

import com.sb.auto.model.UserVO;
import com.sb.auto.service.UserDetailService;
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
public class SampleServiceTest {

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    public void dashboard() {
        UserVO userVO = new UserVO();
        userVO.setUserRole("ADMIN");
        userVO.setUserId("keesun");
        userVO.setUserPw("123");
        userDetailService.insertUser(userVO);

        // DB에서 조회 후 Principal 객체를 반환한다.
        UserDetails userDetails = userDetailService.loadUserByUsername("keesun");
        // 비번 확인하여 token을 만든다.
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "123");
        // AuthenticationManager에서  token으로 인증을 하여 Authentication을 반환
        Authentication authentication = authenticationManager.authenticate(token);
        // SecurityContextHolder 에 Authentication을
        SecurityContextHolder.getContext().setAuthentication(authentication);

        userDetailService.dashboard();
    }

}