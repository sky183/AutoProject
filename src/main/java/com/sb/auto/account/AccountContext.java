package com.sb.auto.account;

public class AccountContext {

    private static final ThreadLocal<UserEntity> ACCOUNT_THREAD_LOCAL = new ThreadLocal<>();

    public static void setAccount(UserEntity userEntity) {
        ACCOUNT_THREAD_LOCAL.set(userEntity);
    }

    public static UserEntity getAccount() {
        return ACCOUNT_THREAD_LOCAL.get();
    }

}
