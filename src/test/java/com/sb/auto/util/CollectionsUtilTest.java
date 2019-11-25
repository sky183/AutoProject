package com.sb.auto.util;

import com.sb.auto.common.util.CollectionsUtil;
import com.sb.auto.model.UserVO;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class CollectionsUtilTest {

    @Test
    public void mapToVoTest() {

        UserVO userVO = new UserVO();
        UserVO userVO1 = new UserVO();
        userVO.setUserRole("ADMIN");
        userVO.setUserId("sky183");
        userVO.setUserPw("1");
        userVO.setPhone("010");
        userVO.setPoint(20000);
        userVO.setUserName("김성범");
        userVO.setUserSeq(1);

        Map<String, Object> map = new HashMap<>();
        map.put("USER_ROLE", "USER");
        map.put("USER_ID", "sky183");
        map.put("USER_PW", "1");
        map.put("PHONE", "010");
        map.put("POINT", 20000);
        map.put("USER_NAME", "김성범");
        map.put("USER_SEQ", 1);

        Map<String, Object> map1 = CollectionsUtil.beanToMap(userVO);
        System.out.println(map1);

        CollectionsUtil.mapToBean(map, userVO1);
        System.out.println(userVO1);

        assertNotNull(map1);
        assertNotNull(userVO1);
    }
}
