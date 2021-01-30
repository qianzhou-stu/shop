package com.andreas.shop.filter;

import com.andreas.shop.pojo.User;
import com.andreas.shop.service.UserService;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author andreaszhou
 * @ClassName AdminFilter
 * @Description 管理员过滤器   描述：     管理员校验过滤器
 * @date 2021/1/30 17:57
 * @Version 1.0
 */
public class AdminFilter implements Filter {
    @Resource
    UserService userServiceImp;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*
         * 设置请求和相应的编码格式-这里设置成utf-8的格式，防止出现乱码的现象。
         * */
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) response).getWriter();
            out.write("{\n"
                    + "    \"status\": 10007,\n"
                    + "    \"msg\": \"用户未登录\",\n"
                    + "    \"data\": null\n"
                    + "}");
            out.flush();
            out.close();
            return;
        }
        Boolean is_admin = userServiceImp.isAdmin(user);
        if (!is_admin) {
            PrintWriter out = new HttpServletResponseWrapper(
                    (HttpServletResponse) response).getWriter();
            out.write("{\n"
                    + "    \"status\": 10009,\n"
                    + "    \"msg\": \"无管理员权限\",\n"
                    + "    \"data\": null\n"
                    + "}");
            out.flush();
            out.close();
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
