package org.wlsw.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Copyright (c) 2018 energy-blockchain.com. All Rights Reserved.
 *
 * @author: wanglei  Date: 2018/4/12 0012 Time: 20:07
 */


public class MobileCodeToken extends UsernamePasswordToken {
    private String code;

    public MobileCodeToken(String username, String password, String code) {
        super(username, password);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
