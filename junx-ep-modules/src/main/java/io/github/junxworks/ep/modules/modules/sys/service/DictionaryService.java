package io.github.junxworks.ep.modules.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import io.github.junxworks.ep.modules.modules.sys.dto.DictionaryPageable;
import io.github.junxworks.ep.modules.modules.sys.vo.DictionaryInfoVo;

/**
 * @Description: 数据字典信息
 * @Author: FengYun
 * @Date: 2019/7/2 16:40
 */

public interface DictionaryService {
	/**
	 * 分页查询数据字典
	 * @param pageable 分页条件
	 * @return 数据字典列表
	 */
	PageInfo<DictionaryInfoVo> getDictionaryListByPage(DictionaryPageable pageable);

	/**
	 * 通过id查询数据字典
	 */
	DictionaryInfoVo getDictionaryInfoById(Long id);

	/**
	 * 新增数据字典
	 */
	int postDictionaryInfo(DictionaryInfoVo dictionaryInfoVo);

	/**
	 * 修改数据字典
	 */
	int putDictionaryInfo(DictionaryInfoVo dictionaryInfoVo);

	/**
	 * 删除数据字典
	 */
	int deleteDictionaryInfo(DictionaryInfoVo dictionaryInfoVo);

	DictionaryInfoVo getDicByCode(DictionaryPageable dto);

	List<DictionaryInfoVo> getParentCode(DictionaryPageable dto);

	Map<String, String> getDictByParentCode(String parentCode);
}
