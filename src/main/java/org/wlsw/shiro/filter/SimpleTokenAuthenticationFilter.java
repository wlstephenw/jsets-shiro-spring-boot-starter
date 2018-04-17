package org.wlsw.shiro.filter;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.wlsw.shiro.token.SimpleToken;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (c) 2018 energy-blockchain.com. All Rights Reserved.
 *
 * @author: wanglei  Date: 2018/4/13 0013 Time: 16:27
 */

public class SimpleTokenAuthenticationFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String headToken = httpServletRequest.getHeader("token");
        if (headToken != null) {
            try {
                SimpleToken simpleToken = new SimpleToken(headToken);
                getSubject(request, response).login(simpleToken);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        onLoginFail(response);
        return false;
    }

    //登录失败时默认返回401状态码
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("login error");
    }
}
