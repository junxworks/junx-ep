/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  OAuth2Realm.java   
 * @Package com.yrxd.security.app.shiro   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-16 17:47:20   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth;

import java.sql.SQLException;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
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
 * shiro权限认证主类
 *
 * @ClassName:  OAuth2Realm
 * @author: 王兴
 * @date:   2019-1-16 17:47:20
 * @since:  v1.0
 */
@Component
public class EPShiroRealm extends AuthorizingRealm {

	@Autowired
	private EPShiroService shiroService;

	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#supports(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof DefaultToken;
	}

	/**
	 * 授权(验证权限时调用)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//用户权限列表
		UserModel user = (UserModel) principals.getPrimaryPrincipal();
		Set<String> permsSet = Sets.newHashSet();
		permsSet.addAll(user.getAuthorizations());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}

	/**
	 * 获取认证信息，当缓存中没有找到认证信息时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		DefaultToken dToken = (DefaultToken) token;
		if (StringUtils.isNull(dToken.getPrincipal())) {
			throw new AuthenticationException(new UnknownTokenException("请重新登录"));
		}
		//暂时用户名和密码
		UserModel user = null;
		try {
			if (DefaultToken.LOGIN_TYPE_DEFAULT == dToken.getLoginType()) {
				user = shiroService.getUser(dToken.getPrincipal(), dToken.getCredentials().toString());
				if (user == null) {
					throw new AuthenticationException("用户名或密码错误");
				}
				user.setAuthorizations(shiroService.getUserAuthorizations(user.getId()));//权限
				user.setRoles(shiroService.getUserRoles(user.getId()));//角色标签
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
