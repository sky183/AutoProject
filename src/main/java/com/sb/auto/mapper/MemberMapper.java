package com.sb.auto.mapper;

import com.sb.auto.security.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    UserEntity findByUserId(String userId);

    int save(UserEntity userEntity);

}
