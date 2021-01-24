/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SJobLogMapper.java   
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

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.scheduler.dto.SJobLogListConditionDto;
import io.github.junxworks.ep.scheduler.entity.SJobLog;
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
public interface SJobLogMapper extends BaseMapper{

	/**
	 * Query object.
	 *
	 * @param id the id
	 * @return the schedule job log entity
	 */
	@Select("select * from s_job_log where id = #{id}")
	SJobLog queryObject(Object id);
	
	/**
	 * Query list.
	 *
	 * @param map the map
	 * @return the list
	 */
	@Select({
		"<script>",
			" select l.*,j.jobName from s_job_log l,s_job j where l.jobId = j.id ",
				"<if test='jobName != null and jobName.trim().length()>0'>",
					" and j.jobName like concat('%', #{jobName}, '%') ",
				"</if>",
				"<if test='beanName != null and beanName.trim().length()>0'>",
					" and j.beanName = #{beanName} ",
				"</if>",
				"<if test='methodName != null and methodName.trim().length()>0'>",
					" and j.methodName = #{methodName} ",
				"</if>",
				"<if test='status != null '>",
					" and l.status = #{status} ",
				"</if>",
				"<if test='startTime != null and startTime.trim().length()>0'>",
					" and l.createTime &gt;= #{startTime} ",
				"</if>",
				"<if test='endTime != null and endTime.trim().length()>0'>",
					" and l.createTime &lt;= #{endTime} ",
				"</if>",
			" order by l.id desc ",
		"</script>"
	})
	List<ScheduleJobLogVo> queryList(SJobLogListConditionDto condition);
	
	/**
	 * Query total.
	 *
	 * @param map the map
	 * @return the int
	 */
	@Select({
		"<script>",
		"select count(1) from s_job_log ",
		"<where>",
			"<if test='jobId != null and jobId.trim().length()>0'>",
				" and jobId = #{jobId} ",
			"</if>",
		"</where>",
		"</script>"
	})
	int queryTotal(Map<String, Object> map);
}
