package com.sb.auto.service;

import com.sb.auto.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class PayPalService {

    MemberMapper memberMapper;

    public PayPalService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public String updatePoint(Map paymap) {
        try {
            memberMapper.paymentSave(paymap);
            memberMapper.paymentUser(paymap);
        } catch (Exception e) {
            return "FAIL";
        }
        return "SUCCESS";
    }
}
