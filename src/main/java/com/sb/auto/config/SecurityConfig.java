package com.sb.auto.config;

import com.sb.auto.account.AccountService;
import com.sb.auto.common.LoggingFilter;
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
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

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

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new LoggingFilter(), WebAsyncManagerIntegrationFilter.class);
        //권한 부여
        http.authorizeRequests()
                .mvcMatchers("/", "/account/**", "/signup").permitAll()
//                .mvcMatchers("/admin").hasRole("ADMIN")
//                .mvcMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()
                .expressionHandler(expressionHandler());
        //로그인 페이지(모든 사용자 접근 가능)
        http.formLogin()
                .loginPage("/login")
                .permitAll();

        http.rememberMe()
                .userDetailsService(accountService)
                .key("remember-me-sample");

        http.httpBasic();
        /**
         * 로그 아웃을 트리거하는 URL (기본값은 "/ logout") CSRF 보호가 활성화 된 경우 (기본값) 요청도 POST 여야합니다.
         * 이는 기본적으로 로그 아웃을 트리거하기 위해 POST "/ logout"이 필요함을 의미합니다.
         * CSRF 보호를 사용하지 않으면 모든 HTTP 메소드가 허용됩니다.
         * CSRF 공격 으로부터 보호하기 위해 상태를 변경하는 모든 작업 (예 : 로그 아웃)에서 HTTP POST를 사용하는 것이 가장 좋습니다 .
         * HTTP GET을 정말로 사용하고 싶다면 logoutRequestMatcher(new AntPathRequestMatcher(logoutUrl, "GET"));
         */
        http.logout()
                .logoutSuccessUrl("/");
        //오류 또는 권한이 없을 경우
        http.exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    String username = principal.getUsername();
                    System.out.println(username + " is denied to access " + request.getRequestURI());
                    response.sendRedirect("/access-denied");
                });

        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
