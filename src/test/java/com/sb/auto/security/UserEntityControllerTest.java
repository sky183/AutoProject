package com.sb.auto.security;

import com.sb.auto.model.UserEntity;
import com.sb.auto.service.CustomUserDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserEntityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Test
    @WithAnonymousUser
    public void index_anonymous() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUser
    public void index_user() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUser
    public void admin_user() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "keesun", roles = "ADMIN")
    public void admin_admin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void login_success() throws Exception {
        String username = "keesun";
        String password = "123";
        UserEntity user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(user.getUserId()).password(password))
                .andExpect(authenticated());
    }

    @Test
    @Transactional
    public void login_success2() throws Exception {
        String username = "keesun";
        String password = "123";
        UserEntity user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(user.getUserId()).password(password))
                .andExpect(authenticated());
    }

    @Test
    @Transactional()
    public void login_fail() throws Exception {
        String username = "keesun";
        String password = "123";
        UserEntity user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(user.getUserId()).password("12345"))
                .andExpect(unauthenticated());
    }

    private UserEntity createUser(String username, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(username);
        userEntity.setUserPassword(password);
        userEntity.setUserRole("USER");
        return customUserDetailService.insertUser(userEntity);
    }

}