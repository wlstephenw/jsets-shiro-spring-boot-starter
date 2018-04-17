package org.wlsw.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Copyright (c) 2018 energy-blockchain.com. All Rights Reserved.
 *
 * @author: wanglei  Date: 2018/4/12 0012 Time: 20:07
 */


public class MobileCodeToken implements AuthenticationToken {
    private String code;
    private String username;
    private String password;

    public MobileCodeToken(String username, String password, String code) {
        this.code = code;
        this.username = username;
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }
}
