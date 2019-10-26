package com.sb.auto.account;

/**
 * ThreadLocal을 사용하여 유저 정보를 가져옴 - 별 쓸데 없음
 */
public class AccountContext {

    private static final ThreadLocal<UserEntity> ACCOUNT_THREAD_LOCAL = new ThreadLocal<>();

    public static void setAccount(UserEntity userEntity) {
        ACCOUNT_THREAD_LOCAL.set(userEntity);
    }

    public static UserEntity getAccount() {
        return ACCOUNT_THREAD_LOCAL.get();
    }

}
