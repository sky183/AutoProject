package com.sb.auto.util;

import com.sb.auto.common.util.CollectionsUtil;
import com.sb.auto.model.UserEntity;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class CollectionsUtilTest {

    @Test
    public void mapToVoTest() {

        UserEntity userEntity = new UserEntity();
        UserEntity userEntity1 = new UserEntity();
        userEntity.setUserRole("ADMIN");
        userEntity.setUserId("sky183");
        userEntity.setUserPassword("1");
        userEntity.setPhone("010");
        userEntity.setPoint(20000);
        userEntity.setUserName("김성범");
        userEntity.setUserSeq(1);

        Map<String, Object> map = new HashMap<>();
        map.put("USER_ROLE", "USER");
        map.put("USER_ID", "sky183");
        map.put("USER_PASSWORD", "1");
        map.put("PHONE", "010");
        map.put("POINT", 20000);
        map.put("USER_NAME", "김성범");
        map.put("USER_SEQ", 1);

        Map<String, Object> map1 = CollectionsUtil.beanToMap(userEntity);
        System.out.println(map1);

        CollectionsUtil.mapToBean(map, userEntity1);
        System.out.println(userEntity1);

        assertNotNull(map1);
        assertNotNull(userEntity1);
    }
}
