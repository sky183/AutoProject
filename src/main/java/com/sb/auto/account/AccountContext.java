package com.sb.auto.account;

public class AccountContext {

    private static final ThreadLocal<AccountEntity> ACCOUNT_THREAD_LOCAL = new ThreadLocal<>();

    public static void setAccount(AccountEntity accountEntity) {
        ACCOUNT_THREAD_LOCAL.set(accountEntity);
    }

    public static AccountEntity getAccount() {
        return ACCOUNT_THREAD_LOCAL.get();
    }

}
