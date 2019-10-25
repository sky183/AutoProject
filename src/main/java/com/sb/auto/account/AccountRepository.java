package com.sb.auto.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUserId(String userId);

}
