/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DictionaryServiceImpl.java   
 * @Package io.github.junxworks.ep.sys.service.impl   
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
package io.github.junxworks.ep.sys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.junxworks.ep.auth.model.UserModel;
import io.github.junxworks.ep.core.exception.BusinessException;
import io.github.junxworks.ep.sys.constants.RecordStatus;
import io.github.junxworks.ep.sys.dto.DictConditionDto;
import io.github.junxworks.ep.sys.dto.SDictDto;
import io.github.junxworks.ep.sys.entity.EpSDict;
import io.github.junxworks.ep.sys.mapper.EpDictMapper;
import io.github.junxworks.ep.sys.service.DictionaryService;
import io.github.junxworks.ep.sys.vo.DictVo;

/**
 * 数据字典服务类实现
 *
 * @ClassName:  DictionaryServiceImpl
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
public class DictionaryServiceImpl implements DictionaryService {

	/** dict mapper. */
	@Autowired
	private EpDictMapper dictMapper;

	/**
	 * 返回 dictionary list by page 属性.
	 *
	 * @param pageable the pageable
	 * @return dictionary list by page 属性
	 */
	public List<DictVo> getDictionaryListByCondition(DictConditionDto condition) {
		return dictMapper.selectByCondition(condition);
	}

	/**
	 * 返回 dictionary info by id 属性.
	 *
	 * @param id the id
	 * @return dictionary info by id 属性
	 */
	public DictVo getDictionaryInfoById(Long id) {
		return dictMapper.selectById(id);
	}

	/**
	 * Delete dictionary info.
	 *
	 * @param DictVo the dictionary info vo
	 * @return the int
	 */
	public int deleteDictById(Long id) {
		EpSDict dictionary = new EpSDict();
		dictionary.setId(id);
		dictionary.setStatus(RecordStatus.DELETED.getValue());
		return dictMapper.updateWithoutNull(dictionary);
	}

	/**
	 * 返回 dic by code 属性.
	 *
	 * @param dto the dto
	 * @return dic by code 属性
	 */
	public DictVo getDicByCode(String parentCode, String dataCode) {
		return dictMapper.selectByCode(parentCode, dataCode);
	}

	/**
	 * 返回 parent code 属性.
	 *
	 * @param dto the dto
	 * @return parent code 属性
	 */
	public List<DictVo> getDictListByParentCode(String parent) {
		return dictMapper.selectByParentCode(parent);
	}

	/**
	 * 返回 dict by parent code 属性.
	 *
	 * @param parentCode the parent code
	 * @return dict by parent code 属性
	 */
	@Override
	public Map<String, String> getDictByParentCode(String parentCode) {
		List<DictVo> dicts = dictMapper.selectByParentCode(parentCode);
		return dicts.stream().collect(Collectors.toMap(DictVo::getDataCode, DictVo::getDataLabel));
	}

	@Override
	public int saveDict(SDictDto dictDto) {
		DictVo info = getDicByCode(dictDto.getParentCode(), dictDto.getDataCode());
		if (info != null && !info.getId().equals(dictDto.getId())) {
			throw new BusinessException("已存在该编码数据字典");
		}
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		EpSDict entity = new EpSDict();
		BeanUtils.copyProperties(dictDto, entity);
		if (dictDto.getId() == null) {
			entity.setCreateUser(user.getId());
			entity.setCreateTime(new Date());
			entity.setStatus(RecordStatus.NORMAL.getValue());
			return dictMapper.insertWithoutNull(entity);
		} else {
			entity.setUpdateUser(user.getId());
			entity.setUpdateTime(new Date());
			return dictMapper.updateWithoutNull(entity);
		}
	}

	@Override
	public Map<String, List<DictVo>> getAllDictionaries() {
		DictConditionDto condition = new DictConditionDto();
		condition.setStatus(RecordStatus.NORMAL.getValue());
		List<DictVo> vos = dictMapper.selectByCondition(condition);
		Map<String, List<DictVo>> res = Maps.newHashMap();
		DictVo lastVo = null;
		List<DictVo> subDict = null;
		for (DictVo vo : vos) {
			String parentCode = vo.getParentCode();
			if (lastVo == null || !lastVo.getParentCode().equals(parentCode)) {
				subDict = Lists.newArrayList();
				res.put(parentCode, subDict);
			}
			subDict.add(vo);
			lastVo = vo;
		}
		return res;
	}
}
