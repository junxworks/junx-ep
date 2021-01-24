/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ParamServiceImpl.java   
 * @Package io.github.junxworks.ep.sys.service.impl   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:41   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.junxworks.ep.sys.entity.SParams;
import io.github.junxworks.ep.sys.mapper.ParamMapper;
import io.github.junxworks.ep.sys.service.ParamService;
import io.github.junxworks.junx.core.util.StringUtils;

@Service
public class ParamServiceImpl implements ParamService {
	private static final String DEFAULT_GROUP = "default";

	@Autowired
	private ParamMapper paramMapper;

	@Override
	public SParams queryParamByGroupAndName(String group, String paramName) {
		if (StringUtils.isNull(group)) {
			group = DEFAULT_GROUP;
		}
		return paramMapper.getParam(group, paramName);
	}

	@Override
	public int updateParam(SParams param) {
		return paramMapper.updateParam(param.getParamGroup(), param.getParamName(), param.getParamValue());
	}
}
