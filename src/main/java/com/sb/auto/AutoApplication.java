package com.sb.auto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

/*@EntityScan(basePackages = {"com.sb.auto.account"})
@EnableJpaRepositories(basePackages = {"com.sb.auto.account"})*/
@SpringBootApplication
@EnableAsync
public class AutoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AutoApplication.class);
    }

/*    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }*/

    public static void main(String[] args) {
        SpringApplication.run(AutoApplication.class, args);
    }

}
