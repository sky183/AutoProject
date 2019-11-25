package com.sb.auto.service;

import com.sb.auto.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class PayPalService {

    UserMapper userMapper;

    public PayPalService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public String updatePoint(Map paymap) {
        try {
            userMapper.paymentSave(paymap);
            userMapper.paymentUser(paymap);
        } catch (Exception e) {
            return "FAIL";
        }
        return "SUCCESS";
    }
}
