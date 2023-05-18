/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DictionaryService.java   
 * @Package io.github.junxworks.ep.sys.service   
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
package io.github.junxworks.ep.sys.service;

import java.util.List;
import java.util.Map;

import io.github.junxworks.ep.sys.dto.DictConditionDto;
import io.github.junxworks.ep.sys.dto.SDictDto;
import io.github.junxworks.ep.sys.vo.DictVo;

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
	List<DictVo> getDictionaryListByCondition(DictConditionDto condition);

	/**
	 * 获取所有数据字典
	 *
	 * @return the all dictionary list
	 */
	Map<String, List<DictVo>> getAllDictionaries();

	/**
	 * 返回 dictionary info by id 属性.
	 *
	 * @param id the id
	 * @return dictionary info by id 属性
	 */
	DictVo getDictionaryInfoById(Long id);

	/**
	 * Put dictionary info.
	 *
	 * @param dictionaryInfoVo the dictionary info vo
	 * @return the int
	 */
	int saveDict(SDictDto dictDto);

	/**
	 * Delete dictionary info.
	 *
	 * @param dictionaryInfoVo the dictionary info vo
	 * @return the int
	 */
	int deleteDictById(Long id);

	/**
	 * 返回 dic by code 属性.
	 *
	 * @param dto the dto
	 * @return dic by code 属性
	 */
	DictVo getDicByCode(String parentCode, String dataCode);

	/**
	 * 返回 parent code 属性.
	 *
	 * @param dto the dto
	 * @return parent code 属性
	 */
	List<DictVo> getDictListByParentCode(String parentCode);

	/**
	 * 返回 dict by parent code 属性.
	 *
	 * @param parentCode the parent code
	 * @return dict by parent code 属性
	 */
	Map<String, String> getDictByParentCode(String parentCode);
}
