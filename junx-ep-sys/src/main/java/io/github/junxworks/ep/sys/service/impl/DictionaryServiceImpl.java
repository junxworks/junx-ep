/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DictionaryServiceImpl.java   
 * @Package io.github.junxworks.ep.sys.service.impl   
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
package io.github.junxworks.ep.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import io.github.junxworks.ep.sys.dto.DictionaryPageable;
import io.github.junxworks.ep.sys.entity.SDict;
import io.github.junxworks.ep.sys.mapper.DictMapper;
import io.github.junxworks.ep.sys.service.DictionaryService;
import io.github.junxworks.ep.sys.vo.DictionaryInfoVo;
import io.github.junxworks.ep.core.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * {类的详细说明}.
 *
 * @ClassName:  DictionaryServiceImpl
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

	/** dict mapper. */
	@Autowired
	private DictMapper dictMapper;

	/**
	 * 返回 dictionary list by page 属性.
	 *
	 * @param pageable the pageable
	 * @return dictionary list by page 属性
	 */
	public PageInfo<DictionaryInfoVo> getDictionaryListByPage(DictionaryPageable pageable) {
		PageUtils.setPage(pageable);
		Page<DictionaryInfoVo> dictionaryList = dictMapper.selectAll(pageable);
		PageInfo<DictionaryInfoVo> voPageInfo = new PageInfo<DictionaryInfoVo>(dictionaryList);
		return voPageInfo;
	}

	/**
	 * 返回 dictionary info by id 属性.
	 *
	 * @param id the id
	 * @return dictionary info by id 属性
	 */
	public DictionaryInfoVo getDictionaryInfoById(Long id) {
		DictionaryInfoVo dctionary = dictMapper.selectById(id);
		return dctionary;
	}

	/**
	 * Post dictionary info.
	 *
	 * @param DictionaryInfoVo the dictionary info vo
	 * @return the int
	 */
	public int postDictionaryInfo(DictionaryInfoVo DictionaryInfoVo) {
		SDict dictionary = new SDict();
		BeanUtils.copyProperties(DictionaryInfoVo, dictionary);
		return dictMapper.insertWithoutNull(dictionary);
	}

	/**
	 * Put dictionary info.
	 *
	 * @param DictionaryInfoVo the dictionary info vo
	 * @return the int
	 */
	public int putDictionaryInfo(DictionaryInfoVo DictionaryInfoVo) {
		SDict dictionary = new SDict();
		BeanUtils.copyProperties(DictionaryInfoVo, dictionary);
		return dictMapper.updateWithNull(dictionary);
	}

	/**
	 * Delete dictionary info.
	 *
	 * @param DictionaryInfoVo the dictionary info vo
	 * @return the int
	 */
	public int deleteDictionaryInfo(DictionaryInfoVo DictionaryInfoVo) {
		SDict dictionary = new SDict();
		BeanUtils.copyProperties(DictionaryInfoVo, dictionary);
		return dictMapper.updateWithoutNull(dictionary);
	}

	/**
	 * 返回 dic by code 属性.
	 *
	 * @param dto the dto
	 * @return dic by code 属性
	 */
	public DictionaryInfoVo getDicByCode(DictionaryPageable dto) {
		return dictMapper.selectByCode(dto);
	}

	/**
	 * 返回 parent code 属性.
	 *
	 * @param dto the dto
	 * @return parent code 属性
	 */
	public List<DictionaryInfoVo> getParentCode(DictionaryPageable dto) {
		return dictMapper.selectParentCode(dto);
	}

	/**
	 * 返回 dict by parent code 属性.
	 *
	 * @param parentCode the parent code
	 * @return dict by parent code 属性
	 */
	@Override
	public Map<String, String> getDictByParentCode(String parentCode) {
		List<DictionaryInfoVo> dicts = dictMapper.selectByParentCode(parentCode);
		return dicts.stream().collect(Collectors.toMap(DictionaryInfoVo::getDataCode, DictionaryInfoVo::getDataValue));
	}
}
