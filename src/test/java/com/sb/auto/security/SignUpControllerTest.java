package com.sb.auto.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignUpControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void signUpForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andDo(print()) //http 내용을 프린트해줌
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("_csrf")));
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
//                .andExpect(content().string("hello world")) //결과 확인
//                .andReturn(); //리턴값을 준다.
                .andDo(print()) //http 내용을 프린트해줌
        ;

        //        mockMvc.perform(get("/"));
//                .andExpect(content().string("hello kim"));
    /*    assertThat(outputCapture.toString())
                .contains("hello")
                .contains("sout");*/
    }

    @Test
    public void processSignUp() throws Exception {
        mockMvc.perform(post("/signup")
                .param("userId", "sky183333")
                .param("userPw", "1")
                .param("userName", "김성범")
                .param("phone", "0107288")
                .with(csrf()))  //Post 요청의 경우 csrf 토큰을 같이 전송해야 테스트 성공한다.
                .andDo(print())
                .andExpect(status().isOk());
    }

}