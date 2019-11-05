package com.sb.auto.config.security;

import com.sb.auto.common.LoggingFilter;
import com.sb.auto.service.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurerAdapter extends org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter {

    UserDetailService userDetailService;

    AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public WebSecurityConfigurerAdapter(UserDetailService userDetailService, AccessDeniedHandler accessDeniedHandler) {
        this.userDetailService = userDetailService;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    /**
     * 권한 계층 설정
     * @return
     */
    public SecurityExpressionHandler expressionHandler() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        //여러 권한 계층을 설정할때 여러 번 나눠서 설정하면 기존 설정을 무시하므로 한번에 설정해야한다.
        //roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_STAFF and ROLE_ADMIN > ROLE_DEVELOPER and ROLE_STAFF > ROLE_USER and ROLE_DEVELOPER > ROLE_USER");
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        return handler;
    }

    /**
     * 정적 자원 요청을 securityFilter에 걸리지 않도록 설정
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .antMatchers("/css/**", "/js/**", "/img/**", "/bootstrap/**", "/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //필터 추가
        http.addFilterBefore(new LoggingFilter(), WebAsyncManagerIntegrationFilter.class);

        //권한 부여
        http.authorizeRequests() //fullyAuthenticated() - 중요한 요청에는 자동 로그인 했어도 인증요구를 한다.
                .mvcMatchers("/admin").access("isFullyAuthenticated() and hasRole('ADMIN')")
                .expressionHandler(expressionHandler()); //AccessDecisionManager 에서 voters를 설정하여 권한 계층을 설정해준다.
        http.authorizeRequests()
                .mvcMatchers("/", "/signup", "/pimang/**").permitAll()
                .mvcMatchers("/user", "/dashboard").hasRole("USER")
                .anyRequest().authenticated() //모든 요청에 대해 인증을 요구한다.
                .expressionHandler(expressionHandler()); //권한 계층 설정

        //로그인 페이지(모든 사용자 접근 가능)
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("userId")
                .passwordParameter("userPw")
                .permitAll();

        //기억하기 기능 활성화
        http.rememberMe()
                .rememberMeParameter("remember-me")  //기본값이 remember-me 이므로 설정안해도 되나 바꾸고 싶은 경우 이와 같이 세팅한다.
                .tokenValiditySeconds(86400) //기본 2주동안 저장되는 쿠키를 1일동안 저장되게 설정
                .userDetailsService(userDetailService)
                .key("uniqueAndSecret");

        /**
         * 웹 브라우저 뿐만 아니라 HTTP 요청 자체로도 인증이 가능하다. 보안에 취약할 수 있으니 https를 추천한다.
         * 인증 정보를 컨텍스트에 저장하는 것이 아니므로 인증이 저장되지는 않는다. 원한다면 인증 유효시간 등을 설정도 가능하다.(비추)
         */
        http.httpBasic();
        http.csrf().ignoringAntMatchers("/pimang/**"); //일부 url에 한해 csrf 토큰 사용하지 않도록 설정
        /**
         * 로그 아웃을 트리거하는 URL (기본값은 "/ logout") CSRF 보호가 활성화 된 경우 (기본값) 요청도 POST 여야합니다.
         * 이는 기본적으로 로그 아웃을 트리거하기 위해 POST "/ logout"이 필요함을 의미합니다.
         * CSRF 보호를 사용하지 않으면 모든 HTTP 메소드가 허용됩니다.
         * CSRF 공격 으로부터 보호하기 위해 상태를 변경하는 모든 작업 (예 : 로그 아웃)에서 HTTP POST를 사용하는 것이 가장 좋습니다 .
         * HTTP GET을 정말로 사용하고 싶다면 logoutRequestMatcher(new AntPathRequestMatcher(logoutUrl, "GET"));
         */
        http.logout()
                .logoutSuccessUrl("/")
                .deleteCookies("remember-me", "JSESSIONID") //쿠키를 삭제한다.
                .clearAuthentication(true) //인증 정보를 지운다.
                .invalidateHttpSession(true); //세션을 지운다.

        //오류 또는 권한이 없을 경우 처리 절차
        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

        //세션 관리
        http.sessionManagement()
//                .sessionFixation().newSession()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) //세션을 만들지 여부(IF_REQUIRED가 기본이므로 세팅 안해도 됨)
                .invalidSessionUrl("/login") //유효하지 않은 세션의 경우 로그인 페이지로
                .maximumSessions(1) //최대 접속 가능 세션
                .maxSessionsPreventsLogin(false) //false시 다른곳에서 접속하면 기존 세션 종료- 새로운 세션에서 로그인 자체를 못하게 하고 싶을때는 true
                .expiredSessionStrategy(e -> {
                    HttpServletResponse response = e.getResponse();
                    Cookie cookie = new Cookie("remember-me", null) ;
                    Cookie session = new Cookie("JSESSIONID", null) ;
                    cookie.setMaxAge(0) ;
                    session.setMaxAge(0) ;
                    response.addCookie(cookie) ;
                    response.addCookie(session) ;
                    response.sendRedirect("/");
                }); //기존 세션 종료시 세션및 쿠키삭제 후 URL 이동
//                .expiredUrl("/login");//기존 세션 종료시 이동할 URL

        // TODO : HttpSessionEventPublisher 동시성 제어를 위해 추가해야하는지 확인

        // TODO ExceptionTranslatorFilter - FilterSecurityInterceptor (AccessDecisionManager, AffirmativeBased)
        // TODO AuthenticationException -> AuthenticationEntryPoint
        // TODO AccessDeniedException -> AccessDeniedHandler

        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
