/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleJobController.java   
 * @Package io.github.junxworks.ep.scheduler.controller   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 17:50:26   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.codegen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.github.junxworks.ep.codegen.dto.EpCgDatasourceCondition;
import io.github.junxworks.ep.codegen.dto.EpCgDatasourceDto;
import io.github.junxworks.ep.codegen.service.CodegenDatasourceService;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.PageUtils;
import io.github.junxworks.ep.sys.annotations.EpLog;
import io.github.junxworks.ep.sys.constants.RecordStatus;

// TODO: Auto-generated Javadoc
/**
 * 数据源管理controller
 */
@RestController
@RequestMapping("/ep/codegen/datasources")
public class EpCodegenDatasourceController {

	/** The codegen data source service. */
	@Autowired
	private CodegenDatasourceService codegenDataSourceService;

	/**
	 * Query datasource list.
	 *
	 * @param condition the condition
	 * @return the result
	 */
	@GetMapping()
	public Result queryDatasourceList(EpCgDatasourceCondition condition) {
		//查询列表数据
		PageUtils.setPage(condition);
		return Result.ok(new PageInfo<>(codegenDataSourceService.queryDatasourceList(condition)));
	}

	/**
	 * Save datasource.
	 *
	 * @param dto the dto
	 * @return the result
	 */
	@EpLog("EP-代码生成-保存数据源")
	@PostMapping()
	public Result saveDatasource(@RequestBody EpCgDatasourceDto dto) {
		codegenDataSourceService.saveDatasource(dto);
		return Result.ok();
	}

	/**
	 * Query datasource entity by id.
	 *
	 * @param id the id
	 * @return the result
	 */
	@GetMapping("/{id}")
	public Result queryDatasourceEntityById(@PathVariable("id") Long id) {
		return Result.ok(codegenDataSourceService.queryDatasourceEntityById(id));
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 * @return the result
	 */
	@EpLog("EP-代码生成-删除数据源")
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable("id") Long id) {
		EpCgDatasourceDto dto = new EpCgDatasourceDto();
		dto.setId(id);
		dto.setStatus(RecordStatus.DELETED.getValue());
		codegenDataSourceService.saveDatasource(dto);
		return Result.ok();
	}
}
