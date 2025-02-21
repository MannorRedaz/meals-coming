package com.mannor.mealscoming.filter;

import com.alibaba.fastjson.JSON;
import com.mannor.mealscoming.common.BaseContext;
import com.mannor.mealscoming.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1、获取本次请求的URI
        String requestURI = request.getRequestURI();
        log.info("拦截到请求：{}", requestURI);
        //定义不需要处理的路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/admin/**",
                "/common/**",
                "/user/login", //移动端登录
                "/user/sendMsg", //移动端发送短信
                "/user/logout",
                "/alipay/notify"
        };
        // 2、判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        // 3、如果不需要处理，则直接放行
        if (check) {
            filterChain.doFilter(request, response);
            log.info("本次请求{}不需要处理", requestURI);
            return;
        }
        // 4-1、判断员工登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("EmployeeId") != null) {
            log.info("用户已经登录，用户id为：{}", request.getSession().getAttribute("EmployeeId"));
            //将id存入线程变量a
            Long employeeId = (Long) request.getSession().getAttribute("EmployeeId");
            BaseContext.setCurrentId(employeeId);
            filterChain.doFilter(request, response);
            return;
        }

        // 4-1、判断用户登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("user") != null) {
            log.info("用户已经登录，用户id为：{}", request.getSession().getAttribute("user"));
            //将id存入线程变量a
            Long userId = (Long) request.getSession().getAttribute("user");

            BaseContext.setCurrentId(userId);
            // 判断用户是否已登录，如果未登录，则重定向到登录页面
            filterChain.doFilter(request, response);
            return;
        }
        // 5、如果未登录则返回未登录结果,通过输出流方式向客户端相应数据
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOT_LOGIN")));
    }

    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
