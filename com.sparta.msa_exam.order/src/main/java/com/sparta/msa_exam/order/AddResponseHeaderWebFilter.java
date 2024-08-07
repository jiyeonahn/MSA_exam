package com.sparta.msa_exam.order;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AddResponseHeaderWebFilter implements Filter {

    @Value("${server.port}")
    String port;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader(
                "Server-Port", port);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
