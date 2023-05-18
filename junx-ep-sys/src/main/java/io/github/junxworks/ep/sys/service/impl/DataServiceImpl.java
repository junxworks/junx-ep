/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DataServiceImpl.java   
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

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.stringtemplate.v4.ST;

import io.github.junxworks.ep.sys.entity.EpSSql;
import io.github.junxworks.ep.sys.mapper.EpDataMapper;
import io.github.junxworks.ep.sys.service.DataService;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * sql查询服务实现类
 *
 * @ClassName:  DataServiceImpl
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
@Service("JunxEPDataService")
public class DataServiceImpl implements DataService {

	/** data mapper. */
	@Autowired
	private EpDataMapper dataMapper;

	/** jdbc template. */
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * 返回 sql template 属性.
	 *
	 * @param uid the uid
	 * @param cond the cond
	 * @return sql template 属性
	 */
	@Override
	public String getSqlTemplate(String uid, Map<String, String> cond) {
		EpSSql sqlTemplate = dataMapper.getSqlByUid(uid);
		if (sqlTemplate == null) {
			return null;
		}
		ST st = new ST(sqlTemplate.getSqlContent(), '{', '}');
		cond.entrySet().forEach(en -> {
			st.add(en.getKey(), en.getValue());
		});
		return st.render();
	}

	/**
	 * 返回 data by SQL uid 属性.
	 *
	 * @param uid the uid
	 * @param cond the cond
	 * @return data by SQL uid 属性
	 * @throws Exception the exception
	 */
	@Override
	public List<Map<String, Object>> getDataBySQLUid(String uid, Map<String, String> cond) throws Exception {
		String sql = getSqlTemplate(uid, cond);
		if (StringUtils.isNull(sql)) {
			throw new BaseRuntimeException("SQL not found by UID \"" + uid + "\"");
		}
		return getDataBySQL(sql, cond);
	}

	@Override
	public List<Map<String, Object>> getDataBySQL(String sql, Map<String, String> cond) throws Exception {
		if (StringUtils.isNull(sql)) {
			throw new BaseRuntimeException("SQL can not be empty.");
		}
		return jdbcTemplate.queryForList(sql, cond);
	}
}
