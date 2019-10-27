package com.sb.auto.mapper;

import com.sb.auto.model.UserEntity;
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
