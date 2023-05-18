package io.github.junxworks.ep.codegen.service;

import java.util.List;

import io.github.junxworks.ep.codegen.dto.EpCgDatasourceCondition;
import io.github.junxworks.ep.codegen.dto.EpCgDatasourceDto;
import io.github.junxworks.ep.codegen.entity.EpCgDatasource;
import io.github.junxworks.ep.codegen.vo.EpCgDatasourceVo;

public interface CodegenDatasourceService {
	List<EpCgDatasourceVo> queryDatasourceList(EpCgDatasourceCondition condition);

	EpCgDatasource queryDatasourceEntityById(Long id);

	EpCgDatasourceVo queryDatasourceEntityByDsId(String dsId);
	
	void saveDatasource(EpCgDatasourceDto dsDto);

}
