package com.sb.auto.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JpaRepository DAO
 */
//@Repository
public interface CustomJpaRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUserId(String userId);

    List<UserEntity> findByUserName(String userName);

    List<UserEntity> findByUserNameLike(String userName);

}
