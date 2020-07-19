/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPRamService.java   
 * @Package io.github.junxworks.ep.auth.ram   
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
package io.github.junxworks.ep.auth.ram;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.junxworks.ep.auth.RamConstants;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.HttpClientUtil;
import io.github.junxworks.ep.core.utils.JsonUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName:  EPRamService
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
public class EPRamService {

	/**
	 * Contact auth center.
	 *
	 * @param authCenterUrl the auth center url
	 * @param key the key
	 * @param secret the secret
	 * @param ctx the ctx
	 * @param uri the uri
	 * @param method the method
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
