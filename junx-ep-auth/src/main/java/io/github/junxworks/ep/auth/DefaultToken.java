/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  SimpleToken.java   
 * @Package com.yrxd.security.app.shiro   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-16 17:49:10   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 简单token对象
 *
 * @ClassName:  SimpleToken
 * @author: 王兴
 * @date:   2019-1-16 17:49:10
 * @since:  v1.0
 */
public class DefaultToken implements AuthenticationToken {
	/** 系统默认的用户名密码登录 */
	public static final int LOGIN_TYPE_DEFAULT = 1;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7135846826802525791L;

	/** 本地用户名密码登录或者其他第三方登录. */
	private int loginType=LOGIN_TYPE_DEFAULT;

	/** 户名. */
	private String principal;

	/** 密码. */
	private String credential;

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	@Override
	public String getPrincipal() {
		return principal;
	}

	@Override
	public Object getCredentials() {
		return credential;
	}
}
