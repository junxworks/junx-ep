/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ScheduleJobLogMapper.java   
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

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import io.github.junxworks.ep.scheduler.entity.ScheduleJobLogEntity;
import io.github.junxworks.ep.scheduler.vo.ScheduleJobLogVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  ScheduleJobLogMapper
 * @author: Michael
 * @date:   2020-7-19 12:18:05
 * @since:  v1.0
 */
@Mapper
public interface ScheduleJobLogMapper {

	/**
	 * Query object.
	 *
	 * @param id the id
	 * @return the schedule job log entity
	 */
	@Select("select * from schedule_job_log where log_id = #{id}")
	ScheduleJobLogEntity queryObject(Object id);
	
	/**
	 * Query list.
	 *
	 * @param map the map
	 * @return the list
	 */
	@Select({
		"<script>",
		" select l.*,j.remark from schedule_job_log l,schedule_job j where l.job_id = j.job_id ",
		"<where>",
			"<if test='jobId != null and jobId.trim().length()>0'>",
				"and l.job_id = #{jobId}",
			"</if>",
		"</where>",
		" order by l.log_id desc ",
		"</script>"
	})
	List<ScheduleJobLogVo> queryList(Map<String, Object> map);
	
	/**
	 * Query total.
	 *
	 * @param map the map
	 * @return the int
	 */
	@Select({
		"<script>",
		"select count(1) from schedule_job_log ",
		"<where>",
			"<if test='jobId != null and jobId.trim().length()>0'>",
				"and job_id = #{jobId}",
			"</if>",
		"</where>",
		"</script>"
	})
	int queryTotal(Map<String, Object> map);
	
	/**
	 * Save.
	 *
	 * @param t the t
	 */
	@Insert({
		"insert into schedule_job_log(`job_id`, `bean_name`, `method_name`, `params`, `status`, `error`, `times`, `create_time`)",
		"values(#{jobId}, #{beanName}, #{methodName}, #{params}, #{status}, #{error}, #{times}, #{createTime})"
	})
	void save(ScheduleJobLogEntity t);
}
