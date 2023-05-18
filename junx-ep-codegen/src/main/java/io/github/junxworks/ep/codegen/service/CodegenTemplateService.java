package io.github.junxworks.ep.codegen.service;

import java.util.List;

import io.github.junxworks.ep.codegen.dto.EpCgTemplateCondition;
import io.github.junxworks.ep.codegen.dto.EpCgTemplateDto;
import io.github.junxworks.ep.codegen.entity.EpCgTemplate;
import io.github.junxworks.ep.codegen.vo.EpCgTemplateVo;

public interface CodegenTemplateService {
	List<EpCgTemplateVo> queryList(EpCgTemplateCondition condition);

	EpCgTemplate queryEntityById(Long id);

	EpCgTemplateVo queryAvailableTemplateByTmpId(String tmpId);

	void saveEntity(EpCgTemplateDto dto);

}
