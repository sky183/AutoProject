package com.sb.auto.form;

import com.sb.auto.account.AccountEntity;
import com.sb.auto.account.AccountService;
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
public class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountService accountService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    public void dashboard() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setRole("ADMIN");
        accountEntity.setUsername("keesun");
        accountEntity.setPassword("123");
        accountService.createNew(accountEntity);

        UserDetails userDetails = accountService.loadUserByUsername("keesun");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "123");
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        sampleService.dashboard();
    }

}