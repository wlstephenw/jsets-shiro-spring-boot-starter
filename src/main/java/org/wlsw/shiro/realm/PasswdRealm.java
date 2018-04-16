/*
 * Copyright 2017-2018 the original author(https://github.com/wj596)
 * 
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */
package org.wlsw.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.wlsw.shiro.config.MessageConfig;
import org.wlsw.shiro.model.Account;
import org.wlsw.shiro.service.ShiroAccountProvider;

import java.util.Set;

/**
 * 基于用户、名密码的控制域
 * 
 * @author wangjie (https://github.com/wj596)
 * @date 2016年6月31日
 */
public class PasswdRealm extends AuthorizingRealm {
	
	private MessageConfig messages;
	private ShiroAccountProvider accountProvider;
	
	public Class<?> getAuthenticationTokenClass() {
		return UsernamePasswordToken.class;
	}
	
	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		if(null==token.getPrincipal()||null==token.getCredentials()){
			throw new AuthenticationException(messages.getMsgAccountPasswordEmpty());
		}
		String account = (String) token.getPrincipal();
		Account accountEntity = this.accountProvider.loadAccount(account);
		if (null == accountEntity) {
			throw new AuthenticationException(messages.getMsgAccountNotExist());
		}
		return new SimpleAuthenticationInfo(account,accountEntity.getPassword(), getName());
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String account = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		Set<String> roles = this.accountProvider.loadRoles(account);
		Set<String> permissions = this.accountProvider.loadPermissions(account);
		if(null!=roles&&!roles.isEmpty())
			info.setRoles(roles);
		if(null!=permissions&&!permissions.isEmpty())
			info.setStringPermissions(permissions);
        return info;  
	}

	public void setMessages(MessageConfig messages) {
		this.messages = messages;
	}
	public void setAccountProvider(ShiroAccountProvider accountProvider) {
		this.accountProvider = accountProvider;
	}
}