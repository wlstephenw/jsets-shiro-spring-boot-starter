package org.wlsw.shiro.service;

/**
 * Copyright (c) 2018 energy-blockchain.com. All Rights Reserved.
 *
 * @author: wanglei  Date: 2018/4/16 0016 Time: 20:36
 */

public class DefaultMobileCodeProvider implements MobileCodeProvider {
    @Override
    public boolean checkCode(String mobileNo, String code) {
        return true;
    }
}
