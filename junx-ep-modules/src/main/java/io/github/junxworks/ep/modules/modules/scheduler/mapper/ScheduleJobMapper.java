package io.github.junxworks.ep.modules.modules.scheduler.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.github.junxworks.ep.modules.modules.scheduler.entity.ScheduleJobEntity;

/**
 * 定时任务
 * 
 * @author wangxing
 * @email 219710904@163.com
 * @date 2016年12月1日 下午10:29:57
 */
@Mapper
public interface ScheduleJobMapper {
	
	@Delete({"<script>",
				"delete from schedule_job where job_id in ",
				"<foreach item='jobId' collection='array' open='(' separator=',' close=')'>",
					"#{jobId}",
				"</foreach>",
			"</script>"})
	public int deleteBatch(Object[] id);

	@Insert("insert into schedule_job(`bean_name`,`method_name`,`params`,`cron_expression`,`status`,`remark`,`create_time`)	values(#{beanName},	#{methodName},#{params},#{cronExpression},#{status},#{remark},#{createTime})")
	@Options(useGeneratedKeys = true, keyProperty = "jobId")
	public void save(ScheduleJobEntity t);
	
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

	@Update({"<script>",
		"update schedule_job set status = #{status} where job_id in ",
		"<foreach item='jobId' collection='list' open='(' separator=',' close=')'>",
			"#{jobId}",
		"</foreach>",
	"</script>"})
	public int updateBatch(Map<String, Object> map);

	@Select({"<script>",
		"select count(1) from schedule_job ",
		"<where>",
			"<if test='beanName != null and beanName.trim().length()>0'>",
				"bean_name like concat('%', #{beanName}, '%') ",
			"</if>",
		"</where>",
	"</script>"})
	public int queryTotal(Map<String, Object> map);
	
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

	@Select("select * from schedule_job where job_id = #{value}")
	public ScheduleJobEntity queryObject(Object id);

}
