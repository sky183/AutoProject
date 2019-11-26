package com.sb.auto.service;

import com.sb.auto.mapper.UserMapper;
import com.sb.auto.model.PayPalVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PayPalService {

    UserMapper userMapper;

    public PayPalService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public String updatePoint(PayPalVO payPalVO ) {
        try {
            userMapper.paymentSave(payPalVO);
            userMapper.paymentUser(payPalVO);
        } catch (Exception e) {
            return "FAIL";
        }
        return "SUCCESS";
    }
}
