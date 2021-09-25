/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPShiroRealm.java   
 * @Package io.github.junxworks.ep.auth   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:42   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth;

import java.sql.SQLException;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

import io.github.junxworks.ep.auth.model.UserModel;
import io.github.junxworks.ep.core.exception.UnknownTokenException;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName: EPShiroRealm
 * @author: Michael
 * @date: 2020-7-19 12:18:42
 * @since: v1.0
 */
@Component
public class EPShiroRealm extends AuthorizingRealm {

	/** shiro service. */
	@Autowired
	private EPShiroService shiroService;

	@Autowired
	private EPShiroConfig shiroConfig;
	
	/**
	 * Supports.
	 *
	 * @param token the token
	 * @return true, if successful
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.realm.AuthenticatingRealm#supports(org.apache.shiro.authc.
	 * AuthenticationToken)
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof DefaultToken;
	}

	/**
	 * Do get authorization info.
	 *
	 * @param principals the principals
	 * @return the authorization info
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 用户权限列表
		UserModel user = (UserModel) principals.getPrimaryPrincipal();
		Set<String> permsSet = Sets.newHashSet();
		permsSet.addAll(user.getAuthorizations());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}

	/**
	 * Do get authentication info.
	 *
	 * @param token the token
	 * @return the authentication info
	 * @throws AuthenticationException the authentication exception
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		DefaultToken dToken = (DefaultToken) token;
		if (StringUtils.isNull(dToken.getPrincipal())) {
			throw new AuthenticationException(new UnknownTokenException("请重新登录"));
		}
		// 暂时用户名和密码
		UserModel user = null;
		try {
			if (DefaultToken.LOGIN_TYPE_DEFAULT == dToken.getLoginType()) {
				user = shiroService.getUser(dToken.getPrincipal(), dToken.getCredentials().toString());
				if (user == null) {
					int _failedCount = 1;
					Object failedCount = SecurityUtils.getSubject().getSession().getAttribute(AuthConstants.LOGIN_FAILED_COUNT_PREFIX + dToken.getPrincipal());
					if (failedCount != null) {
						_failedCount = (int) failedCount;
						_failedCount++;
						if(shiroConfig.getLoginFailThreshold()==_failedCount) {
							shiroService.lockUser(dToken.getPrincipal());
							SecurityUtils.getSubject().getSession().setAttribute(AuthConstants.LOGIN_FAILED_COUNT_PREFIX + dToken.getPrincipal(), 0);
						}
					}
					SecurityUtils.getSubject().getSession().setAttribute(AuthConstants.LOGIN_FAILED_COUNT_PREFIX + dToken.getPrincipal(), _failedCount);
					SecurityUtils.getSubject().getSession().setAttribute(AuthConstants.CHECK_VERIFICATION_CODE, true);
					throw new IncorrectCredentialsException("用户名或密码错误");
				} else if (user.getStatus() == AuthConstants.USER_STATUS_DISABLED) {
					throw new DisabledAccountException("用户账户不可用");
				} else if (user.getStatus() == AuthConstants.USER_STATUS_LOCKED) {
					throw new LockedAccountException("用户账户已锁定");
				}
				user.setAuthorizations(shiroService.getUserAuthorizations(user.getId()));// 权限
				user.setRoles(shiroService.getUserRoles(user.getId()));// 角色标签
			} else {
				throw new AuthenticationException("不支持的登录方式");
			}
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, token.getCredentials(), getName());
			return info;
		} catch (SQLException e) {
			throw new AuthenticationException("查询用户数据失败", e);
		}
	}
}
