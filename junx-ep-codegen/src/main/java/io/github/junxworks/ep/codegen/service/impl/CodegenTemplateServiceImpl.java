package io.github.junxworks.ep.codegen.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.junxworks.ep.auth.model.UserModel;
import io.github.junxworks.ep.codegen.dto.EpCgTemplateCondition;
import io.github.junxworks.ep.codegen.dto.EpCgTemplateDto;
import io.github.junxworks.ep.codegen.entity.EpCgTemplate;
import io.github.junxworks.ep.codegen.mapper.CodegenTemplateMapper;
import io.github.junxworks.ep.codegen.service.CodegenTemplateService;
import io.github.junxworks.ep.codegen.vo.EpCgTemplateVo;
import io.github.junxworks.ep.sys.constants.RecordStatus;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import io.github.junxworks.junx.core.util.StringUtils;

@Service
public class CodegenTemplateServiceImpl implements CodegenTemplateService {

	@Autowired
	private CodegenTemplateMapper templateMapper;

	@Override
	public List<EpCgTemplateVo> queryList(EpCgTemplateCondition condition) {
		return templateMapper.queryList(condition);
	}

	@Override
	public EpCgTemplate queryEntityById(Long id) {
		return templateMapper.selectEntityByID(EpCgTemplate.class, id);
	}

	@Override
	public void saveEntity(EpCgTemplateDto dto) {
		if (StringUtils.notNull(dto.getTmpId())) {
			EpCgTemplateVo exists = templateMapper.queryTemplateByTmpId(dto.getTmpId());
			if (exists != null && exists.getId() != dto.getId()) {
				throw new BaseRuntimeException("重复的模板标识:" + dto.getTmpId());
			}
		}
		EpCgTemplate entity = new EpCgTemplate();
		BeanUtils.copyProperties(dto, entity);
		entity.setOutputName(entity.getOutputName().trim());
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		Long userId = user.getId();
		if (entity.getId() == null) {
			entity.setCreateTime(new Date());
			entity.setCreateUser(userId);
			entity.setStatus(RecordStatus.NORMAL.getValue());
			templateMapper.insertWithoutNull(entity);
		} else {
			entity.setUpdateTime(new Date());
			entity.setUpdateUser(userId);
			templateMapper.updateWithoutNull(entity);
		}
	}

	@Override
	public EpCgTemplateVo queryAvailableTemplateByTmpId(String tmpId) {
		return templateMapper.queryTemplateByTmpId(tmpId);
	}

}
