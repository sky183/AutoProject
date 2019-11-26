package com.sb.auto.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class RequestUtil {

    /**
     * Request 반환
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest();
    }

    /**
     * Response 반환
     * @return
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getResponse();
    }

    public static Cookie[] getCookies() {
        return getRequest().getCookies();
    }
    public static HttpSession getSession() {
        return getRequest().getSession();
    }
    public static HttpSession getSession(boolean bool) {
        return getRequest().getSession(bool);
    }
    public static Enumeration getParameterNames() {
       return getRequest().getParameterNames();
    }
    public static String getParameter(String paramName) {
        return getRequest().getParameter(paramName);
    }
    public static String getRemoteAddr() {
        return getRequest().getRemoteAddr();
    }





}
