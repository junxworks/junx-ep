/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleJobMapper.java   
 * @Package io.github.junxworks.ep.scheduler.mapper   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:05   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
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
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.github.junxworks.ep.scheduler.entity.ScheduleJobEntity;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleJobMapper
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
@Mapper
public interface ScheduleJobMapper {
	
	/**
	 * Delete batch.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Delete({"<script>",
				"delete from schedule_job where job_id in ",
				"<foreach item='jobId' collection='array' open='(' separator=',' close=')'>",
					"#{jobId}",
				"</foreach>",
			"</script>"})
	public int deleteBatch(Object[] id);

	/**
	 * Save.
	 *
	 * @param t the t
	 */
	@Insert("insert into schedule_job(`bean_name`,`method_name`,`params`,`cron_expression`,`status`,`remark`,`create_time`)	values(#{beanName},	#{methodName},#{params},#{cronExpression},#{status},#{remark},#{createTime})")
	@Options(useGeneratedKeys = true, keyProperty = "jobId")
	public void save(ScheduleJobEntity t);
	
	/**
	 * Update.
	 *
	 * @param t the t
	 * @return the int
	 */
	@Update({"<script>",
		"update schedule_job ",
		"<set> ",
			"<if test='beanName != null'>`bean_name` = #{beanName}, </if>",
			"<if test='methodName != null'>`method_name` = #{methodName}, </if>",
			"<if test='params != null'>`params` = #{params}, </if>",
			"<if test='cronExpression != null'>`cron_expression` = #{cronExpression}, </if>",
			"<if test='status != null'>`status` = #{status}, </if>",
			"<if test='remark != null'>`remark` = #{remark}, </if>",
		"</set>",
		"where job_id = #{jobId}",
		"</script>"})
	public int update(ScheduleJobEntity t);

	/**
	 * Update batch.
	 *
	 * @param map the map
	 * @return the int
	 */
	@Update({"<script>",
		"update schedule_job set status = #{status} where job_id in ",
		"<foreach item='jobId' collection='list' open='(' separator=',' close=')'>",
			"#{jobId}",
		"</foreach>",
	"</script>"})
	public int updateBatch(Map<String, Object> map);

	/**
	 * Query total.
	 *
	 * @param map the map
	 * @return the int
	 */
	@Select({"<script>",
		"select count(1) from schedule_job ",
		"<where>",
			"<if test='beanName != null and beanName.trim().length()>0'>",
				"bean_name like concat('%', #{beanName}, '%') ",
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
		"select * from schedule_job ",
		"<where>",
			"<if test='beanName != null and beanName.trim().length()>0'>",
				"bean_name like concat('%', #{beanName}, '%') ",
			"</if>",
			"<if test='remark != null and remark.trim().length()>0'>",
				"and remark like concat('%', #{remark}, '%') ",
			"</if>",
		"</where>",
	"</script>"})
	public List<ScheduleJobEntity> queryList(Map<String, Object> map);

	/**
	 * Query object.
	 *
	 * @param id the id
	 * @return the schedule job entity
	 */
	@Select("select * from schedule_job where job_id = #{value}")
	public ScheduleJobEntity queryObject(Object id);

}
