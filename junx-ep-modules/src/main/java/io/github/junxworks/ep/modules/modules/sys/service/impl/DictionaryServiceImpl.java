package io.github.junxworks.ep.modules.modules.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.github.junxworks.ep.modules.modules.sys.dto.DictionaryPageable;
import io.github.junxworks.ep.modules.modules.sys.entity.SDict;
import io.github.junxworks.ep.modules.modules.sys.mapper.DictMapper;
import io.github.junxworks.ep.modules.modules.sys.service.DictionaryService;
import io.github.junxworks.ep.modules.modules.sys.vo.DictionaryInfoVo;
import io.github.junxworks.ep.core.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 数据字典信息
 * @Author: FengYun
 * @Date: 2019/7/1 10:40
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

	@Autowired
	private DictMapper dictMapper;

	/**
	 * 分页查询菜单信息
	 */
	public PageInfo<DictionaryInfoVo> getDictionaryListByPage(DictionaryPageable pageable) {
		PageUtils.setPage(pageable);
		Page<DictionaryInfoVo> dictionaryList = dictMapper.selectAll(pageable);
		PageInfo<DictionaryInfoVo> voPageInfo = new PageInfo<DictionaryInfoVo>(dictionaryList);
		return voPageInfo;
	}

	/**
	 * 通过id查询菜单信息
	 */
	public DictionaryInfoVo getDictionaryInfoById(Long id) {
		DictionaryInfoVo dctionary = dictMapper.selectById(id);
		return dctionary;
	}

	/**
	 * 新增菜单信息
	 */
	public int postDictionaryInfo(DictionaryInfoVo DictionaryInfoVo) {
		SDict dictionary = new SDict();
		BeanUtils.copyProperties(DictionaryInfoVo, dictionary);
		return dictMapper.insertWithoutNull(dictionary);
	}

	/**
	 * 修改菜单信息
	 */
	public int putDictionaryInfo(DictionaryInfoVo DictionaryInfoVo) {
		SDict dictionary = new SDict();
		BeanUtils.copyProperties(DictionaryInfoVo, dictionary);
		return dictMapper.updateWithNull(dictionary);
	}

	/**
	 * 删除菜单信息
	 */
	public int deleteDictionaryInfo(DictionaryInfoVo DictionaryInfoVo) {
		SDict dictionary = new SDict();
		BeanUtils.copyProperties(DictionaryInfoVo, dictionary);
		return dictMapper.updateWithoutNull(dictionary);
	}

	public DictionaryInfoVo getDicByCode(DictionaryPageable dto) {
		return dictMapper.selectByCode(dto);
	}

	public List<DictionaryInfoVo> getParentCode(DictionaryPageable dto) {
		return dictMapper.selectParentCode(dto);
	}

	@Override
	public Map<String, String> getDictByParentCode(String parentCode) {
		List<DictionaryInfoVo> dicts = dictMapper.selectByParentCode(parentCode);
		return dicts.stream().collect(Collectors.toMap(DictionaryInfoVo::getDataCode, DictionaryInfoVo::getDataValue));
	}
}
