package com.sb.auto.mapper;

import com.sb.auto.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MemberMapper {
    UserEntity findByUserId(String userId);

    int save(UserEntity userEntity);

    int updateUser(UserEntity userEntity);

    int paymentSave(Map map);

    int paymentUser(Map userMap);
}
