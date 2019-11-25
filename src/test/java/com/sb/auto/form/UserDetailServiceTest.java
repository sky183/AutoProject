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
public class UserDetailServiceTest {

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    public void loadUserByUsername() {
        UserVO userVO = new UserVO();
        userVO.setUserRole("ADMIN");
        userVO.setUserId("sky183");
        userVO.setUserPw("1");
        userVO.setPhone("010");
        userVO.setPoint(20000);
        userVO.setUserName("김성범");
        userVO.setUserSeq(1);

        userDetailService.insertUser(userVO);

        UserDetails userDetails = userDetailService.loadUserByUsername("sky183");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "123");
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        userDetailService.dashboard();

    }


}
