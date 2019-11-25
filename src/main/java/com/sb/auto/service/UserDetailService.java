package com.sb.auto.service;

import com.sb.auto.common.util.SecurityLogger;
import com.sb.auto.config.security.User;
import com.sb.auto.mapper.JpaRepository;
import com.sb.auto.mapper.UserMapper;
import com.sb.auto.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class UserDetailService implements org.springframework.security.core.userdetails.UserDetailsService {

    JpaRepository jpaRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailService(JpaRepository jpaRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.jpaRepository = jpaRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Principal 객체 반환 - 메서드에서 주입받는 Principal 은 여기서 반환하는 객체이다.
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserVO userVO = userMapper.findByUserId(userId);
        if (userVO == null) {
            throw new UsernameNotFoundException(userId);
        }
        return new User(userVO);
    }

    public UserVO insertUser(UserVO userVO) {
        userVO.encodePassword(passwordEncoder);
        this.userMapper.save(userVO);
        return userVO;
    }


    //@PreAuthorize("hasRole(ROLE_USER)") //메서드 호출 이전 이후에 권한 확인 가능. 스프링 EL 사용하여 메서드와 리턴값 검증 가능
    //@PostAuthorize("ROLE_USER") //메서드 호출 이전 이후에 권한 확인 가능. 스프링 EL 사용하여 메서드와 리턴값 검증 가능
    //@RolesAllowed("ROLE_USER") //메서드 호출 이전에 권한 확인. 스프링 EL 사용 못함
    @Secured({"ROLE_USER", "ROLE_ADMIN"}) //메서드 호출 이전에 권한 확인. 스프링 EL 사용 못함
    public void dashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Iterator it = userDetails.getAuthorities().iterator();
        System.out.println("===============");
        System.out.println(authentication);
        System.out.println(userDetails.getUsername());
        System.out.println(it.next());
    }

    @Async
    public void asyncService() {
        SecurityLogger.log("Async Service");
        System.out.println("Async service is called.");
    }

}
