package com.sb.auto.common;

import org.springframework.util.StopWatch;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoggingFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        StopWatch stopWatch = new StopWatch();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        stopWatch.start(httpRequest.getRequestURI() + "  " + httpRequest.getRemoteAddr());
        chain.doFilter(request, response);
        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());
    }
}
