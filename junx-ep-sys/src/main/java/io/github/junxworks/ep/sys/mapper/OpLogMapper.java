/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  OpLogMapper.java   
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

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.sys.dto.SystemLogConditionDto;
import io.github.junxworks.ep.sys.vo.SystemLogInfoVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  OpLogMapper
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
@Mapper
public interface OpLogMapper extends BaseMapper{

	/**
	 * Select by id.
	 *
	 * @param id the id
	 * @return the system log info vo
	 */
	@Select("select t.*,u.name from s_op_log t LEFT JOIN s_user u on t.userId=u.id where t.id=#{id}")
	public SystemLogInfoVo selectById(Long id);

	/**
	 * Delete by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Delete("delete from s_op_log where id=#{id}")
	public int deleteById(Long id);
	
	/**
	 * Select all.
	 *
	 * @param entity the entity
	 * @return the page
	 */
	@Select("<script>" +
			"select t.*,u.name from s_op_log t LEFT JOIN s_user u on t.userId=u.id  where 1=1" +
			"<if test='createDate!=null and createDate.length>0 '> " +
			"and TO_DAYS(t.createTime) = TO_DAYS(#{createDate}) " +
			"</if>" +
			"<if test='name!=null and name.length>0 '> " +
			"and u.name like CONCAT('%',#{name},'%') " +
			"</if>" +
			"<if test='opName!=null and opName.length>0 '> " +
			"and t.operation like CONCAT('%',#{opName},'%') " +
			"</if>" +
			" order by t.id desc"+
			"</script>")
	public Page<SystemLogInfoVo> selectAll(SystemLogConditionDto entity);

}