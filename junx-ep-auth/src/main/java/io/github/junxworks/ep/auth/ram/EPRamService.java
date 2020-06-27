/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  EPRamService.java   
 * @Package com.yrxd.security.ep   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-11-13 17:11:06   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth.ram;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.junxworks.ep.auth.RamConstants;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.HttpClientUtil;
import io.github.junxworks.ep.core.utils.JsonUtils;

/**
 * RAM服务类
 *
 * @ClassName:  EPRamService
 * @author: 王兴
 * @date:   2019-11-13 17:11:08
 * @since:  v1.0
 */
public class EPRamService {

	/**
	 * 请求RAM中心，进行RAM校验
	 *
	 * @param authCenterUrl RAM认证中心URL地址
	 * @param key ram key
	 * @param secret ram secret
	 * @param ctx 当前请求上下文
	 * @param uri 当前请求URI
	 * @param method 当前http请求method
	 * @return the result
	 */
	public Result contactAuthCenter(String authCenterUrl, String key, String secret, String ctx, String uri, String method) {
		Map<String, String> param = Maps.newHashMap();
		param.put(RamConstants.RAM_HEADER_ACCESSKEY, key);
		param.put(RamConstants.RAM_HEADER_ACCESSSECRET, secret);
		param.put(RamConstants.RAM_CONTEXT, ctx);
		param.put(RamConstants.RAM_URI, uri);
		param.put(RamConstants.RAM_METHOD, method);
		String retString = HttpClientUtil.sendHttpGet(authCenterUrl, param);
		Result res = JsonUtils.parseObject(Result.class, retString);
		return res;
	}
}
