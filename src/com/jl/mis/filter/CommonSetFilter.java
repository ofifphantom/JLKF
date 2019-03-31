package com.jl.mis.filter;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/6/9 17:27
 */
public class CommonSetFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//解决跨域问题
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        HttpServletResponse httpServletResponse =(HttpServletResponse)servletResponse;
        // 指定允许其他域名访问
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 响应类型
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST");
        // 响应头设置
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");

        filterChain.doFilter(servletRequest, servletResponse);
    }
    @Override
    public void destroy() {

    }
}
