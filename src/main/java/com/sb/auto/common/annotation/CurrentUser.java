package com.sb.auto.common.annotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 현재 유저를 파라미터로 받아올 수 있는 애노테이션
 * 현재 유저가 익명이 아닌 경우 userVO(doamin 객체)를 직접 가져온다
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : userVO")
public @interface CurrentUser {
}
