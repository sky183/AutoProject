package com.sb.auto.mapper;

import com.sb.auto.model.UserVO;

import java.util.List;

/**
 * JpaRepository DAO
 */
public interface JpaRepository extends org.springframework.data.jpa.repository.JpaRepository<UserVO, Integer> {

    UserVO findByUserId(String userId);

    List<UserVO> findByUserName(String userName);

    List<UserVO> findByUserNameLike(String userName);

}
