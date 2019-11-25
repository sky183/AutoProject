package com.sb.auto.service;

import com.sb.auto.mapper.PimangMapper;
import com.sb.auto.model.EtcUserVO;
import com.sb.auto.model.StockVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

/**
 * 스프링부트를 실행하지 않고도 테스트할 수 있는 설정!!
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
classes = {PimangAPIService.class, PimangMapper.class})
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PimangAPIServiceTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @MockBean
    private PimangMapper pimangMapper;

    @MockBean
    private PimangAPIService pimangAPIService;

    @Autowired
    private MockMvc mockMvc;

    private StockVO stockVO;
    EtcUserVO etcUserVO;


    @Before
    public void setUp() {
        this.stockVO = new StockVO();
        stockVO.setUserId("pimang");
        stockVO.setUserPw("pimang123");
        this.etcUserVO = new EtcUserVO();
        etcUserVO.setUserId("pimang");
        etcUserVO.setUserPw("pimang123");
        /**
         * when() then()과 같다.
         */
        given(pimangMapper.selectUser("pimang")).willReturn(this.etcUserVO);
        /**
         * when() - 메서드 호출 조건, thenReturn() 그 조건을 충족할때 리턴할 값
         * selectUser를 호출하면 etcUser를 리턴하도록 설정해주는 메서드
         */
        when(pimangAPIService.selectUser(stockVO)).thenReturn(etcUserVO);
        when(pimangAPIService.validateUser(stockVO)).thenReturn("성공");
    }


    @Test
    public void selectUser() throws Exception {
        log.info(pimangAPIService.selectUser(stockVO).toString());

    }

    @Test
    public void validateUser() {
        log.info(pimangAPIService.validateUser(stockVO));
    }

    @Test
    public void selectStock() {
    }
}