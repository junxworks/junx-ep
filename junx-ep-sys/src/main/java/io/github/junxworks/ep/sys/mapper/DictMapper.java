/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DictMapper.java   
 * @Package io.github.junxworks.ep.sys.mapper   
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
package io.github.junxworks.ep.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.sys.dto.DictConditionDto;
import io.github.junxworks.ep.sys.vo.DictVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  DictMapper
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@Mapper
public interface DictMapper extends BaseMapper{

	/**
	 * Select by id.
	 *
	 * @param id the id
	 * @return the dictionary info vo
	 */
	@Select("select * from ep_s_dict where id=#{id}")
	public DictVo selectById(Long id);

	/**
	 * Delete by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Delete("delete from ep_s_dict where id=#{id}")
	public int deleteById(Long id);
	
	/**
	 * Select all.
	 *
	 * @param entity the entity
	 * @return the page
	 */
	@Select("<script>" +
				"select * from ep_s_dict where 1=1 " +
					"<if test='status!=null '> " +
					" and status = #{status} " +
					"</if>" +
					"<if test='parentCode!=null and parentCode.length>0 '> " +
					" and parent_code =#{parentCode} " +
					"</if>" +
					"<if test='dataLabel!=null and dataLabel.length>0 '> " +
					" and data_label like CONCAT('%',#{dataLabel},'%')  " +
					"</if>" +
					"<if test='dataCode!=null and dataCode.length>0 '> " +
					" and data_code =#{dataCode}   " +
					"</if>" +
				" order by parent_code desc,sort asc"+
			"</script>")
	public List<DictVo> selectByCondition(DictConditionDto entity);

	/**
	 * Select by code.
	 *
	 * @param entity the entity
	 * @return the dictionary info vo
	 */
	@Select(" select * from ep_s_dict where status=0 and data_code =#{dataCode} and parent_code=#{parentCode}")
	public DictVo selectByCode(@Param("parentCode")String parentCode,@Param("dataCode")String dataCode);

	/**
	 * Select by parent code.
	 *
	 * @param parent_code the parent code
	 * @return the list
	 */
	@Select("select * from ep_s_dict where parent_code=#{parentCode} and status=0")
	public List<DictVo> selectByParentCode(@Param("parentCode")String parentCode);
}