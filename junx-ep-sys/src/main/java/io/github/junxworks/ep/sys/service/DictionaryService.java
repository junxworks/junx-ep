/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DictionaryService.java   
 * @Package io.github.junxworks.ep.sys.service   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:17:48   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import io.github.junxworks.ep.sys.dto.DictionaryPageable;
import io.github.junxworks.ep.sys.vo.DictionaryInfoVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  DictionaryService
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */

public interface DictionaryService {
	
	/**
	 * 返回 dictionary list by page 属性.
	 *
	 * @param pageable the pageable
	 * @return dictionary list by page 属性
	 */
	PageInfo<DictionaryInfoVo> getDictionaryListByPage(DictionaryPageable pageable);

	/**
	 * 返回 dictionary info by id 属性.
	 *
	 * @param id the id
	 * @return dictionary info by id 属性
	 */
	DictionaryInfoVo getDictionaryInfoById(Long id);

	/**
	 * Post dictionary info.
	 *
	 * @param dictionaryInfoVo the dictionary info vo
	 * @return the int
	 */
	int postDictionaryInfo(DictionaryInfoVo dictionaryInfoVo);

	/**
	 * Put dictionary info.
	 *
	 * @param dictionaryInfoVo the dictionary info vo
	 * @return the int
	 */
	int putDictionaryInfo(DictionaryInfoVo dictionaryInfoVo);

	/**
	 * Delete dictionary info.
	 *
	 * @param dictionaryInfoVo the dictionary info vo
	 * @return the int
	 */
	int deleteDictionaryInfo(DictionaryInfoVo dictionaryInfoVo);

	/**
	 * 返回 dic by code 属性.
	 *
	 * @param dto the dto
	 * @return dic by code 属性
	 */
	DictionaryInfoVo getDicByCode(DictionaryPageable dto);

	/**
	 * 返回 parent code 属性.
	 *
	 * @param dto the dto
	 * @return parent code 属性
	 */
	List<DictionaryInfoVo> getParentCode(DictionaryPageable dto);

	/**
	 * 返回 dict by parent code 属性.
	 *
	 * @param parentCode the parent code
	 * @return dict by parent code 属性
	 */
	Map<String, String> getDictByParentCode(String parentCode);
}
