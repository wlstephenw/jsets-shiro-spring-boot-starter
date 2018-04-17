package org.wlsw.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.wlsw.shiro.model.Account;
import org.wlsw.shiro.service.ShiroAccountProvider;
import org.wlsw.shiro.service.SimpleTokenManager;
import org.wlsw.shiro.token.SimpleToken;

/**
 * Copyright (c) 2018 energy-blockchain.com. All Rights Reserved.
 *
 * @author: wanglei  Date: 2018/4/12 0012 Time: 19:46
 */

public class TokenShiroRealm extends AuthorizingRealm {

    private ShiroAccountProvider accountProvider;
    private SimpleTokenManager simpleTokenManager;

    public TokenShiroRealm(ShiroAccountProvider accountProvider, SimpleTokenManager simpleTokenManager) {
        this.accountProvider = accountProvider;
        this.simpleTokenManager = simpleTokenManager;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof SimpleToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //根据用户名查找角色，请根据需求实现
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        SimpleToken simpleToken = (SimpleToken) authenticationToken;
        Account account = accountProvider.loadAccount(simpleToken.getUserId());
        if (account == null)
            return null;
        if (!simpleTokenManager.checkToken(simpleToken))
            throw new AuthenticationException("simpleToken auth failed");
        return new SimpleAuthenticationInfo(account.getAccount(), simpleToken.getCredentials(), getName());
    }
}
