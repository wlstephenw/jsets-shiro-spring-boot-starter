package org.wlsw.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.wlsw.shiro.token.MobileCodeToken;

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

public class MobileCodeAuthenticationFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) {
        Subject subject = this.getSubject(request, response);
        return subject.isAuthenticated();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String phoneNo = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        String verifyCode = httpServletRequest.getParameter("verifyCode");
        try {
            MobileCodeToken token = new MobileCodeToken(phoneNo, password, verifyCode);
            getSubject(request, response).login(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        onLoginFail(response);
        return false;
    }

    //登录失败时默认返回401状态码
    private void onLoginFail(ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
