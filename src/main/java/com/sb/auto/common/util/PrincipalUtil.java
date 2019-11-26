package com.sb.auto.common.util;

import com.sb.auto.config.security.User;
import com.sb.auto.model.UserVO;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class PrincipalUtil {

    /**
     * User반환
     * @return User
     */
    public static User getUser() {
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    /**
     * UserVO 반환
     * @return UserVO
     */
    public static UserVO getUserVO() {
        return getUser().getUserVO();
    }
    /**
     * Authorities반환
     * @return Collection
     */
    public static Collection getAutorities(){
        return getUser().getAuthorities();
    }

}
