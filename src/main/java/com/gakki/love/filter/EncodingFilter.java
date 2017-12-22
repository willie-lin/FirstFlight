package com.gakki.love.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/12/18
 * \* Time: 22:33
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("编码过录取");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }
}