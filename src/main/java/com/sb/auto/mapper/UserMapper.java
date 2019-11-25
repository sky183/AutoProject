package com.sb.auto.mapper;

import com.sb.auto.model.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper {
    UserVO findByUserId(String userId);

    int save(UserVO userVO);

    int updateUser(UserVO userVO);

    int paymentSave(Map map);

    int paymentUser(Map userMap);
}
