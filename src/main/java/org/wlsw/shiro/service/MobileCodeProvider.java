package org.wlsw.shiro.service;

/**
 * Copyright (c) 2018 energy-blockchain.com. All Rights Reserved.
 *
 * @author: wanglei  Date: 2018/4/16 0016 Time: 20:35
 */

public interface MobileCodeProvider {
    boolean checkCode(String mobileNo, String code);
}
