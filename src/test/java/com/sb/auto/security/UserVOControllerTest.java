package com.sb.auto.security;

import com.sb.auto.model.UserVO;
import com.sb.auto.service.UserDetailService;
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
@AutoConfigureMockMvc //가상의 인증된 유저 또는 인증되지 않은 유저로 사용 가능
public class UserVOControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserDetailService userDetailService;

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
        UserVO user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(user.getUserId()).password(password))
                .andExpect(authenticated());
    }

    @Test
    @Transactional
    public void login_success2() throws Exception {
        String username = "keesun";
        String password = "123";
        UserVO user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(user.getUserId()).password(password))
                .andExpect(authenticated());
    }

    @Test
    @Transactional()
    public void login_fail() throws Exception {
        String username = "keesun";
        String password = "123";
        UserVO user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(user.getUserId()).password("12345"))
                .andExpect(unauthenticated());
    }

    private UserVO createUser(String username, String password) {
        UserVO userVO = new UserVO();
        userVO.setUserId(username);
        userVO.setUserPw(password);
        userVO.setUserRole("USER");
        return userDetailService.insertUser(userVO);
    }

}