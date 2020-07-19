/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DictMapper.java   
 * @Package io.github.junxworks.ep.sys.mapper   
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
package io.github.junxworks.ep.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.sys.dto.DictionaryPageable;
import io.github.junxworks.ep.sys.vo.DictionaryInfoVo;

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
	@Select("select * from s_dict where id=#{id}")
	public DictionaryInfoVo selectById(Long id);

	/**
	 * Delete by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Delete("delete from s_dict where id=#{id}")
	public int deleteById(Long id);
	
	/**
	 * Select all.
	 *
	 * @param entity the entity
	 * @return the page
	 */
	@Select("<script>" +
			"select * from s_dict where 1=1" +
			"<if test='status!=null '> " +
			"and status = #{status} " +
			"</if>" +
			"<if test='parentCode!=null and parentCode.length>0 '> " +
			"and parentCode like CONCAT('%',#{parentCode},'%') " +
			"</if>" +
			"<if test='dataValue!=null and dataValue.length>0 '> " +
			"and dataValue like CONCAT('%',#{dataValue},'%')  " +
			"</if>" +
			"<if test='dataCode!=null and dataCode.length>0 '> " +
			"and dataCode =#{dataCode}   " +
			"</if>" +
			" order by parentCode desc,sort asc"+
			"</script>")
	public Page<DictionaryInfoVo> selectAll(DictionaryPageable entity);

	/**
	 * Select by code.
	 *
	 * @param entity the entity
	 * @return the dictionary info vo
	 */
	@Select("<script>" +
			"select * from s_dict where 1=1" +
			"<if test='status!=null '> " +
			"and status = #{status} " +
			"</if>" +
			"<if test='parentCode!=null and parentCode.length>0 '> " +
			"and parentCode=#{parentCode} " +
			"</if>" +
			"<if test='dataValue!=null and dataValue.length>0 '> " +
			"and dataValue=#{dataValue}  " +
			"</if>" +
			"<if test='dataCode!=null and dataCode.length>0 '> " +
			"and dataCode =#{dataCode}   " +
			"</if>" +
			" order by parentCode desc,sort asc"+
			"</script>")
	public DictionaryInfoVo selectByCode(DictionaryPageable entity);

	/**
	 * Select parent code.
	 *
	 * @param entity the entity
	 * @return the list
	 */
	@Select("<script> SELECT * FROM s_dict where 1=1" +
			"<if test='parentCode!=null and parentCode.length>0 '> " +
			"and parentCode=#{parentCode} " +
			"</if>"+
			" GROUP BY parentCode"+ "</script>")
	public List<DictionaryInfoVo> selectParentCode(DictionaryPageable entity);

	/**
	 * Select by parent code.
	 *
	 * @param parentCode the parent code
	 * @return the list
	 */
	@Select("select * from s_dict where parentCode=#{parentCode} and status=0")
	public List<DictionaryInfoVo> selectByParentCode(@Param("parentCode")String parentCode);
	
}