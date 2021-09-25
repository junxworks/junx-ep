/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  LoginConstants.java   
 * @Package io.github.junxworks.ep.sys.constants   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-3-28 13:57:58   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth;

/**
 * 系统登录相关常量
 *
 * @ClassName: LoginConstants
 * @author: Michael
 * @date: 2021-3-28 13:57:58
 * @since: v1.0
 */
public class AuthConstants {

	/** 常量 VERIFY_CODE. */
	public static final String VERIFICATION_CODE = "verificationCode";

	/** 常量 CHECK_VERIFY_CODE. */
	public static final String CHECK_VERIFICATION_CODE = "checkVerificationCode";

	/** 常量 USERNAME. */
	public static final String USERNAME = "username";

	/** 常量 PASSWD. */
	public static final String PASSWD = "password";

	public static final int USER_STATUS_NORMAL = 0;

	public static final int USER_STATUS_DISABLED = 1;

	public static final int USER_STATUS_LOCKED = 2;

	public static final String LOGIN_FAILED_COUNT_PREFIX = "UserLoginFailedCount-";
}
