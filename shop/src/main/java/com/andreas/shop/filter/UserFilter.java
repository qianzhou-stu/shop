package com.andreas.shop.filter;

import com.andreas.shop.pojo.User;
import io.netty.util.internal.ObjectUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author andreaszhou
 * @ClassName UserFilter
 * @Description TODO
 * @date 2021/2/1 15:20
 * @Version 1.0
 */
public class UserFilter implements Filter {
    /*向外提供一个可以存储当前用户的接口*/
    public static User current_user = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*设置编码的格式*/
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        current_user = (User) session.getAttribute("user");
        if (current_user == null) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//            PrintWriter writer = new HttpServletResponseWrapper(httpServletResponse).getWriter();
            PrintWriter out = httpServletResponse.getWriter();
            out.write("{\n"
                    + "    \"status\": 10007,\n"
                    + "    \"msg\": \"用户未登录\",\n"
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
