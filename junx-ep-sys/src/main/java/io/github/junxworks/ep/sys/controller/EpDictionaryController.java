/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DictionaryController.java   
 * @Package io.github.junxworks.ep.sys.controller   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:41   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.PageUtils;
import io.github.junxworks.ep.sys.annotations.EpLog;
import io.github.junxworks.ep.sys.dto.DictConditionDto;
import io.github.junxworks.ep.sys.dto.SDictDto;
import io.github.junxworks.ep.sys.service.DictionaryService;
import io.github.junxworks.ep.sys.vo.DictVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  DictionaryController
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
@RestController
@RequestMapping("/ep/sys/dictionaries")
public class EpDictionaryController {

	/** dictionary service. */
	@Autowired
	private DictionaryService dictionaryService;

	/**
	 * 返回 dictionary list 属性.
	 *
	 * @param pageable the pageable
	 * @return dictionary list 属性
	 */
	@GetMapping()
	public Result getDictionaryList(DictConditionDto condition) {
		PageUtils.setPage(condition);
		return Result.ok(new PageInfo<DictVo>(dictionaryService.getDictionaryListByCondition(condition)));
	}

	@GetMapping("/{parentCode}/maps")
	public Result getDictionaryMapByParentCode(@PathVariable("parentCode") String parentCode) {
		return Result.ok(dictionaryService.getDictByParentCode(parentCode));
	}

	@GetMapping("/total")
	public Result getAllDictionaryList() {
		return Result.ok(dictionaryService.getAllDictionaries());
	}

	/**
	 * Save dictionary info.
	 *
	 * @param dictionaryInfo the dictionary info
	 * @return the result
	 */
	@PostMapping()
	@EpLog("EP-系统支撑-保存字典")
	public Result saveDictionaryInfo(@RequestBody SDictDto dictDto) {
		return Result.ok(dictionaryService.saveDict(dictDto));
	}

	/**
	 * 返回 dictionary info by id 属性.
	 *
	 * @param id the id
	 * @return dictionary info by id 属性
	 */
	@GetMapping("/{id}")
	public Result getDictionaryInfoById(@PathVariable Long id) {
		return Result.ok(dictionaryService.getDictionaryInfoById(id));
	}

	/**
	 * Put dictionary info.
	 *
	 * @param id the id
	 * @return the result
	 */
	@DeleteMapping("/{id}")
	@EpLog("EP-系统支撑-删除字典")
	public Result putDictionaryInfo(@PathVariable("id") Long id) {
		dictionaryService.deleteDictById(id);
		return Result.ok();
	}
}
