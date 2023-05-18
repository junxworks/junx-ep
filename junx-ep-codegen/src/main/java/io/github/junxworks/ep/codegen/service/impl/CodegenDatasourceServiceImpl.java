package io.github.junxworks.ep.codegen.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.junxworks.ep.auth.model.UserModel;
import io.github.junxworks.ep.codegen.dto.EpCgDatasourceCondition;
import io.github.junxworks.ep.codegen.dto.EpCgDatasourceDto;
import io.github.junxworks.ep.codegen.entity.EpCgDatasource;
import io.github.junxworks.ep.codegen.mapper.CodegenDatasourceMapper;
import io.github.junxworks.ep.codegen.service.CodegenDatasourceService;
import io.github.junxworks.ep.codegen.vo.EpCgDatasourceVo;
import io.github.junxworks.ep.sys.constants.RecordStatus;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import io.github.junxworks.junx.core.util.StringUtils;

@Service
public class CodegenDatasourceServiceImpl implements CodegenDatasourceService {

	@Autowired
	private CodegenDatasourceMapper codegenDataSourceMapper;

	@Override
	public List<EpCgDatasourceVo> queryDatasourceList(EpCgDatasourceCondition condition) {
		return codegenDataSourceMapper.queryDatasourceList(condition);
	}

	@Override
	public void saveDatasource(EpCgDatasourceDto dsDto) {
		if (StringUtils.notNull(dsDto.getDsId())) {
			EpCgDatasourceVo exists = codegenDataSourceMapper.queryDatasourceByDsId(dsDto.getDsId());
			if (exists != null && exists.getId() != dsDto.getId()) {
				throw new BaseRuntimeException("重复的数据源标识:" + dsDto.getDsId());
			}
		}
		EpCgDatasource ds = new EpCgDatasource();
		BeanUtils.copyProperties(dsDto, ds);
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		Long userId = user.getId();
		if (ds.getId() == null) {
			ds.setCreateTime(new Date());
			ds.setCreateUser(userId);
			ds.setStatus(RecordStatus.NORMAL.getValue());
			codegenDataSourceMapper.insertWithoutNull(ds);
		} else {
			ds.setUpdateTime(new Date());
			ds.setUpdateUser(userId);
			codegenDataSourceMapper.updateWithoutNull(ds);
		}
	}

	@Override
	public EpCgDatasource queryDatasourceEntityById(Long id) {
		return codegenDataSourceMapper.selectEntityByID(EpCgDatasource.class, id);
	}

	@Override
	public EpCgDatasourceVo queryDatasourceEntityByDsId(String dsId) {
		// TODO Auto-generated method stub
		return codegenDataSourceMapper.queryDatasourceByDsId(dsId);
	}

}
