package com.sb.auto.mapper;

import com.sb.auto.model.PayPalVO;
import com.sb.auto.model.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserVO findByUserId(String userId);

    int save(UserVO userVO);

    int updateUser(UserVO userVO);

    int paymentSave(PayPalVO payPalVO);

    int paymentUser(PayPalVO payPalVO);
}
