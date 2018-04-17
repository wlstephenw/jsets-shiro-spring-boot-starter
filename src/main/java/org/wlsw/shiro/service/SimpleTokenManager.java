package org.wlsw.shiro.service;

import org.wlsw.shiro.token.SimpleToken;

/**
 * Copyright (c) 2018 energy-blockchain.com. All Rights Reserved.
 *
 * @author: wanglei  Date: 2018/4/12 0012 Time: 22:00
 */

public interface SimpleTokenManager {
    /**
     * 创建一个token关联上指定用户
     *
     * @param userId 指定用户的id
     * @return 生成的token
     */
    SimpleToken createToken(String userId);

    /**
     * get token from cache
     *
     * @param userId user id
     * @return
     */
    SimpleToken getToken(String userId);

    /**
     * 检查token是否有效
     *
     * @param model token
     * @return 是否有效
     */
    boolean checkToken(SimpleToken model);

    /**
     * 清除token
     *
     * @param userId 登录用户的id
     */
    void deleteToken(String userId);
}
