package io.github.junxworks.ep.modules.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.stringtemplate.v4.ST;

import io.github.junxworks.ep.modules.modules.sys.entity.SSqls;
import io.github.junxworks.ep.modules.modules.sys.mapper.DataMapper;
import io.github.junxworks.ep.modules.modules.sys.service.DataService;

import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import io.github.junxworks.junx.core.util.StringUtils;

@Service
public class DataServiceImpl implements DataService {

	@Autowired
	private DataMapper dataMapper;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public String getSqlTemplate(String uid, Map<String, String> cond) {
		SSqls sqlTemplate = dataMapper.getSqlByUid(uid);
		if (sqlTemplate == null) {
			return null;
		}
		ST st = new ST(sqlTemplate.getSqlContent(), '{', '}');
		cond.entrySet().forEach(en -> {
			st.add(en.getKey(), en.getValue());
		});
		return st.render();
	}

	@Override
	public List<Map<String, Object>> getDataBySQLUid(String uid, Map<String, String> cond) throws Exception {
		String sql = getSqlTemplate(uid, cond);
		if (StringUtils.isNull(sql)) {
			throw new BaseRuntimeException("SQL not found by UID \"" + uid + "\"");
		}
		return jdbcTemplate.queryForList(sql, cond);
	}
}
