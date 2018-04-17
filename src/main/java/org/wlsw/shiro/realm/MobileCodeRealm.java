package org.wlsw.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;
import org.wlsw.shiro.model.Account;
import org.wlsw.shiro.service.MobileCodeProvider;
import org.wlsw.shiro.service.ShiroAccountProvider;
import org.wlsw.shiro.token.MobileCodeToken;

/**
 * Copyright (c) 2018 energy-blockchain.com. All Rights Reserved.
 *
 * @author: wanglei  Date: 2018/4/12 0012 Time: 19:46
 */

public class MobileCodeRealm extends AuthorizingRealm {

    private MobileCodeProvider mobileCodeProvider;

    private ShiroAccountProvider accountProvider;

    public MobileCodeRealm(MobileCodeProvider mobileCodeProvider, ShiroAccountProvider accountProvider) {
        this.mobileCodeProvider = mobileCodeProvider;
        this.accountProvider = accountProvider;
    }

    public Class<?> getAuthenticationTokenClass() {
        return MobileCodeToken.class;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        MobileCodeToken token = (MobileCodeToken) authenticationToken;
        Object credentials;
        String phone = authenticationToken.getPrincipal().toString();
        Account account = accountProvider.loadAccount(phone);
        if (account == null)
            return null;
        if (token.getCode() != null && !StringUtils.isEmpty(token.getCode())) {
            // TODO check code
            if (mobileCodeProvider.checkCode(phone, token.getCode())) {
                // if the code is right, we do not need to check the password, use the token's password directly.
                credentials = token.getCredentials();
            } else
                return null;
        } else {
            credentials = account.getPassword();
        }
        return new SimpleAuthenticationInfo(account.getAccount(), credentials, getName());
    }
}
