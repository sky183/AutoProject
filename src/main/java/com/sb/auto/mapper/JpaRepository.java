package com.sb.auto.mapper;

import com.sb.auto.model.UserEntity;

import java.util.List;

/**
 * JpaRepository DAO
 */
//@Repository
public interface JpaRepository extends org.springframework.data.jpa.repository.JpaRepository<UserEntity, Integer> {

    UserEntity findByUserId(String userId);

    List<UserEntity> findByUserName(String userName);

    List<UserEntity> findByUserNameLike(String userName);

}
