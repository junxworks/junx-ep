/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ParamServiceImpl.java   
 * @Package io.github.junxworks.ep.sys.service.impl   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021年11月20日 下午2:26:37   
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

import io.github.junxworks.ep.sys.entity.EpSParam;
import io.github.junxworks.ep.sys.mapper.EpParamMapper;
import io.github.junxworks.ep.sys.service.ParamService;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * 系统参数服务实现类
 *
 * @ClassName:  ParamServiceImpl
 * @author: Michael
 * @date:   2021年11月20日 下午2:26:37
 * @since:  v1.0
 */
public class ParamServiceImpl implements ParamService {
	
	/** 常量 DEFAULT_GROUP. */
	private static final String DEFAULT_GROUP = "default";

	/** param mapper. */
	@Autowired
	private EpParamMapper paramMapper;

	/**
	 * Query param by group and name.
	 *
	 * @param group the group
	 * @param paramName the param name
	 * @return the ep S param
	 */
	@Override
	public EpSParam queryParamByGroupAndName(String group, String paramName) {
		if (StringUtils.isNull(group)) {
			group = DEFAULT_GROUP;
		}
		return paramMapper.getParam(group, paramName);
	}

	/**
	 * Update param.
	 *
	 * @param param the param
	 * @return the int
	 */
	@Override
	public int updateParam(EpSParam param) {
		return paramMapper.updateParam(param.getParamGroup(), param.getParamName(), param.getParamValue());
	}
}
