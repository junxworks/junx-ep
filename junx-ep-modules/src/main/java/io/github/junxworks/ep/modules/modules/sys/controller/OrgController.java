/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  OrgController.java   
 * @Package io.github.junxworks.ep.modules.modules.sys.controller   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-12-13 11:47:06   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.modules.modules.sys.controller;

import java.util.List;

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
import io.github.junxworks.ep.modules.annotations.OpLog;
import io.github.junxworks.ep.modules.modules.sys.dto.OrgDto;
import io.github.junxworks.ep.modules.modules.sys.service.OrgService;
import io.github.junxworks.ep.modules.modules.sys.vo.TreeSelectVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  OrgController
 * @author: 王兴
 * @date:   2019-12-13 11:47:06
 * @since:  v1.0
 */
@RestController
@RequestMapping("/ep/sys/orgs")
public class OrgController {

	/** org service. */
	@Autowired
	private OrgService orgService;

	/**
	 * 组织列表
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

	@DeleteMapping("/{id}")
	@OpLog("删除组织")
	public Result updateOrg(@PathVariable("id") Long id) {
		return Result.ok(orgService.deleteOrg(id));
	}
	
	@GetMapping("/tree/{rootNo}")
	public Result queryOrgTree(@PathVariable("rootNo") String rootNo) {
		return Result.ok(orgService.queryOrgTree(rootNo));
	}
	
	@GetMapping("/tree-select/{rootNo}")
	public List<TreeSelectVo> queryTreeSelect(@PathVariable("rootNo") String rootNo) {
		return orgService.queryTreeSelect(rootNo);
	}
}
