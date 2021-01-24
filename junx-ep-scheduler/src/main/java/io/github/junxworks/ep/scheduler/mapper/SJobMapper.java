/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SJobMapper.java   
 * @Package io.github.junxworks.ep.scheduler.mapper   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 17:50:25   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.scheduler.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.scheduler.dto.SJobListConditionDto;
import io.github.junxworks.ep.scheduler.entity.SJob;
import io.github.junxworks.ep.scheduler.vo.ScheduleJobVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleJobMapper
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
@Mapper
public interface SJobMapper extends BaseMapper{
	
	/**
	 * Delete batch.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Delete({"<script>",
				"delete from s_job where id in ",
				"<foreach item='jobId' collection='array' open='(' separator=',' close=')'>",
					"#{jobId}",
				"</foreach>",
			"</script>"})
	public int deleteBatch(Object[] id);

	/**
	 * Update batch.
	 *
	 * @param map the map
	 * @return the int
	 */
	@Update({"<script>",
		"update s_job set status = #{status} where id in ",
		"<foreach item='jobId' collection='list' open='(' separator=',' close=')'>",
			"#{jobId}",
		"</foreach>",
	"</script>"})
	public int updateStatusBatch(Map<String, Object> map);

	/**
	 * Query total.
	 *
	 * @param map the map
	 * @return the int
	 */
	@Select({"<script>",
		"select count(1) from s_job ",
		"<where>",
			"<if test='beanName != null and beanName.trim().length()>0'>",
				" beanName like concat('%', #{beanName}, '%') ",
			"</if>",
		"</where>",
	"</script>"})
	public int queryTotal(Map<String, Object> map);
	
	/**
	 * Query list.
	 *
	 * @param map the map
	 * @return the list
	 */
	@Select({"<script>",
		"select j.*,u.name `creatUserName`,u2.name `updateUserName` from s_job j left join s_user u2 on j.updateUser=u2.id,s_user u ",
		"where j.createUser=u.id",
			"<if test='beanName != null and beanName.trim().length()>0'>",
				" and j.beanName like concat('%', #{beanName}, '%') ",
			"</if>",
			"<if test='jobName != null and jobName.trim().length()>0'>",
				" and j.jobName like concat('%', #{jobName}, '%') ",
			"</if>",
	"</script>"})
	public List<ScheduleJobVo> queryList(SJobListConditionDto condition);

	/**
	 * Query object.
	 *
	 * @param id the id
	 * @return the schedule job entity
	 */
	@Select("select * from s_job where id = #{value}")
	public SJob queryObject(Object id);

}
