package com.sb.auto.config.security;

import com.sb.auto.model.UserVO;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * ThreadLocal을 사용하여 유저 정보를 가져옴 - 별 쓸데 없음
 */
public class AccountContext {

    public static void main(String[] args) {
        System.out.println(JdbcUtils.commonDatabaseName("camelCase"));
    }

    private static final ThreadLocal<UserVO> ACCOUNT_THREAD_LOCAL = new ThreadLocal<>();

    public static void setAccount(UserVO userVO) {
        ACCOUNT_THREAD_LOCAL.set(userVO);

    }

    public static UserVO getAccount() {
        return ACCOUNT_THREAD_LOCAL.get();
    }

}
