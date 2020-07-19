/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  OrgController.java   
 * @Package io.github.junxworks.ep.sys.controller   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:17:47   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.sys.annotations.OpLog;
import io.github.junxworks.ep.sys.dto.OrgDto;
import io.github.junxworks.ep.sys.service.OrgService;

/**
 * {类的详细说明}.
 *
 * @ClassName:  OrgController
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
@RestController
@RequestMapping("/ep/sys/orgs")
public class OrgController {

	/** org service. */
	@Autowired
	private OrgService orgService;

	/**
	 * Query org list.
	 *
	 * @param dto the dto
	 * @return the result
	 */
	@GetMapping()
	public Result queryOrgList(OrgDto dto) {
		return Result.ok(orgService.queryOrgList(dto));
	}

	/**
	 * Query org by id.
	 *
	 * @param id the id
	 * @return the result
	 */
	@GetMapping("/{id}")
	public Result queryOrgById(@PathVariable("id") Long id) {
		return Result.ok(orgService.queryOrgById(id));
	}

	/**
	 * Adds the org.
	 *
	 * @param dto the dto
	 * @return the result
	 */
	@PostMapping()
	@OpLog("新增组织")
	public Result addOrg(@RequestBody OrgDto dto) {
		return Result.ok(orgService.saveOrg(dto));
	}

	/**
	 * Update org.
	 *
	 * @param dto the dto
	 * @return the result
	 */
	@PutMapping()
	@OpLog("修改组织")
	public Result updateOrg(@RequestBody OrgDto dto) {
		return Result.ok(orgService.updateOrg(dto));
	}

	/**
	 * Update org.
	 *
	 * @param id the id
	 * @return the result
	 */
	@DeleteMapping("/{id}")
	@OpLog("删除组织")
	public Result updateOrg(@PathVariable("id") Long id) {
		return Result.ok(orgService.deleteOrg(id));
	}

	/**
	 * Query org tree.
	 *
	 * @param rootNo the root no
	 * @return the result
	 */
	@GetMapping("/tree/{rootNo}")
	public Result queryOrgTree(@PathVariable("rootNo") String rootNo) {
		return Result.ok(orgService.queryOrgTree(rootNo));
	}
	
	/**
	 * Query tree select.
	 *
	 * @param rootNo the root no
	 * @return the result
	 */
	@GetMapping("/tree-select/{rootNo}")
	public Result queryTreeSelect(@PathVariable("rootNo") String rootNo) {
		return Result.ok(orgService.queryTreeSelect(rootNo));
	}

}
