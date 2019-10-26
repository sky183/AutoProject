package com.sb.auto.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JpaRepository DAO
 */
//@Repository
public interface AccountRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUserId(String userId);

    List<UserEntity> findByUserName(String userName);

    List<UserEntity> findByNameLike(String userName);

}
