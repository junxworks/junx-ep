package io.github.junxworks.ep.codegen.service;

import java.io.File;
import java.util.List;

import io.github.junxworks.ep.codegen.core.Table;
import io.github.junxworks.ep.codegen.dto.EpCgGeneratorCondition;
import io.github.junxworks.ep.codegen.dto.EpCgGeneratorDto;
import io.github.junxworks.ep.codegen.dto.FileGenerateCondition;
import io.github.junxworks.ep.codegen.dto.TableListCondition;
import io.github.junxworks.ep.codegen.entity.EpCgGenerator;
import io.github.junxworks.ep.codegen.vo.EpCgGeneratorVo;

public interface CodegenGeneratorService {
	List<EpCgGeneratorVo> queryList(EpCgGeneratorCondition condition);

	EpCgGenerator queryEntityById(Long id);

	void saveEntity(EpCgGeneratorDto dto);

	List<Table> queryTableList(TableListCondition condition);

	File generateFiles(FileGenerateCondition condition) throws Exception;
}
