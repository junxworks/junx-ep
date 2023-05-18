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

import io.github.junxworks.ep.codegen.dto.EpCgTemplateCondition;
import io.github.junxworks.ep.codegen.dto.EpCgTemplateDto;
import io.github.junxworks.ep.codegen.service.CodegenTemplateService;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.PageUtils;
import io.github.junxworks.ep.sys.annotations.EpLog;
import io.github.junxworks.ep.sys.constants.RecordStatus;

/**
 * 模板管理
 */
@RestController
@RequestMapping("/ep/codegen/templates")
public class EpCodegenTemplateController {
	@Autowired
	private CodegenTemplateService templateService;

	/**
	 * Query list.
	 *
	 * @param condition the condition
	 * @return the result
	 */
	@GetMapping()
	public Result queryList(EpCgTemplateCondition condition) {
		//查询列表数据
		PageUtils.setPage(condition);
		return Result.ok(new PageInfo<>(templateService.queryList(condition)));
	}

	/**
	 * Save .
	 *
	 * @param dto the dto
	 * @return the result
	 */
	@EpLog("EP-代码生成-保存模板")
	@PostMapping()
	public Result saveEntity(@RequestBody EpCgTemplateDto dto) {
		templateService.saveEntity(dto);
		return Result.ok();
	}

	/**
	 * Query entity by id.
	 *
	 * @param id the id
	 * @return the result
	 */
	@GetMapping("/{id}")
	public Result queryDatasourceEntityById(@PathVariable("id") Long id) {
		return Result.ok(templateService.queryEntityById(id));
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 * @return the result
	 */
	@EpLog("EP-代码生成-删除模板")
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable("id") Long id) {
		EpCgTemplateDto dto = new EpCgTemplateDto();
		dto.setId(id);
		dto.setStatus(RecordStatus.DELETED.getValue());
		templateService.saveEntity(dto);
		return Result.ok();
	}
}
