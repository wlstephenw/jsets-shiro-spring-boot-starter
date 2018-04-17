package org.wlsw.shiro.token;

/**
 * Copyright (c) 2018 energy-blockchain.com. All Rights Reserved.
 *
 * @author: wanglei  Date: 2018/4/12 0012 Time: 21:57
 */

public class SimpleToken extends StatelessToken {
    //用户id
    private String userId;
    //随机生成的uuid
    private String token;

    public SimpleToken(String userId, String token) {
        super(null);
        this.userId = userId;
        this.token = token;
    }

    public SimpleToken(String host, String userId, String token) {
        super(host);
        this.userId = userId;
        this.token = token;
    }

    public SimpleToken(String authentication) throws Exception {
        super(null);
        if (authentication == null || authentication.length() == 0) {
            throw new Exception();
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            throw new Exception();
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        this.userId = param[0];
        this.token = param[1];
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return userId + "_" + token;
    }

    @Override
    public Object getPrincipal() {
        return this.userId;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
