package com.sb.auto.config.security;

import com.sb.auto.common.util.PrincipalUtil;
import com.sb.auto.model.UserVO;
import com.sb.auto.service.UserDetailService;
import org.springframework.stereotype.Component;

@Component
public class UserChangeFactory {

    private UserDetailService userDetailService;

    public UserChangeFactory(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    public UserVO newUserVO() {
        return this.newUserVO(PrincipalUtil.getUserVO().getUserId());
    }

    public UserVO newUserVO(String userId) {
        User user = (User)userDetailService.loadUserByUsername(userId);
        UserVO userVO = user.getUserVO();
        userVO.setUserPw(null);
        PrincipalUtil.getUser().setUserVO(userVO);
        return userVO;
    }

}
