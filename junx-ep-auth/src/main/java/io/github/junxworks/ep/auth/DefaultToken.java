/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DefaultToken.java   
 * @Package io.github.junxworks.ep.auth   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:41   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * {类的详细说明}.
 *
 * @ClassName:  DefaultToken
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
public class DefaultToken implements AuthenticationToken {
	
	/** 常量 LOGIN_TYPE_DEFAULT. */
	public static final int LOGIN_TYPE_DEFAULT = 1;

	/** 常量 serialVersionUID. */
	private static final long serialVersionUID = -7135846826802525791L;

	/** login type. */
	private int loginType=LOGIN_TYPE_DEFAULT;

	/** principal. */
	private String principal;

	/** credential. */
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
