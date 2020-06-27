/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhepc.com.cn
 * @Title:  RamConstants.java   
 * @Package com.epxd.constants.ram   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-11-12 15:56:46   
 * @version V1.0 
 * @Copepight: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth;

public class RamConstants {
	public static final String RAM_HEADER_ACCESSKEY = "epRamKey";

	public static final String RAM_HEADER_ACCESSSECRET = "epRamSecret";

	public static final String RAM_URI = "epRamURI";

	public static final String RAM_CONTEXT = "epRamCTX";

	public static final String RAM_METHOD = "epRamMethod";

	public static final String RAM_URL_PATH = "/ram/authentications";

	/** 常量 RAM_ACCOUNT_PREFIX. 帐号前缀 */
	public static final String RAM_ACCOUNT_PREFIX = "ram-acc-";

	/** 常量 RAM_AUTH_PREFIX，直接鉴权前缀. */
	public static final String RAM_AUTH_PREFIX = "ram-auth-";

	/** 常量 RAM_AUTH_ANT_PREFIX.通配符鉴权前缀 */
	public static final String RAM_AUTH_ANT_PREFIX = "ram-auth-ant-";

	/** 常量 RAM_AUTH_ANT_PREFIX.URI定义分隔符 */
	public static final String RAM_AUTH_URI_SEPARATER = ";";

}
