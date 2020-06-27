package io.github.junxworks.ep.modules.modules.scheduler.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import io.github.junxworks.ep.modules.modules.scheduler.entity.ScheduleJobLogEntity;
import io.github.junxworks.ep.modules.modules.scheduler.vo.ScheduleJobLogVo;

/**
 * 定时任务日志
 * 
 * @author wangxing
 * @email 219710904@163.com
 * @date 2016年12月1日 下午10:30:02
 */
@Mapper
public interface ScheduleJobLogMapper {

	@Select("select * from schedule_job_log where log_id = #{id}")
	ScheduleJobLogEntity queryObject(Object id);
	
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
	
	@Insert({
		"insert into schedule_job_log(`job_id`, `bean_name`, `method_name`, `params`, `status`, `error`, `times`, `create_time`)",
		"values(#{jobId}, #{beanName}, #{methodName}, #{params}, #{status}, #{error}, #{times}, #{createTime})"
	})
	void save(ScheduleJobLogEntity t);
}
