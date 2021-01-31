package io.github.junxworks.ep.fs.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.fs.entity.SFile;
import io.github.junxworks.ep.fs.entity.SFileThumb;

@Mapper
public interface FSMapper extends BaseMapper{
	@Select("select * from sys_file")
	public List<SFile> getAll();

	@Select("select * from sys_file where id=#{id}")
	public SFile findById(String id);

	@Select("select * from sys_file where file_group=#{fileGroup}")
	public List<SFile> findByGroup(String fileGroup);

	@Select("select * from sys_file where org_no=#{orgNo}")
	public List<SFile> findByOrg(String orgNo);

	@Select("delete from sys_file where id=#{id}")
	public void deleteById(String id);

	@Select("delete from sys_file where file_group=#{fileGroup}")
	public void deleteByGroup(String fileGroup);

	@Select({ 
		"<script>", 
			"SELECT * FROM sys_file", 
			"<where>", 
				"<if test='fileGroup != null'>", 
					"and `file_group` = #{fileGroup}", 
				"</if>", 
				"<if test='fileName != null'>", 
					"and `file_name` like concat('%',#{fileName},'%')", 
				"</if>", 
			"</where>", 
			"<choose>", 
				"<when test='sidx != null and sidx.trim().length()>0'>",
					"order by ${sidx} ${order}", 
				"</when>", 
				"<otherwise>", 
					"order by id desc", 
				"</otherwise>", 
			"</choose>", 
		"</script>" })
	public List<SFile> queryFileList(Map<String, Object> params);

	@Select("select * from sys_file_thumb where fileId=#{fileId} and width=#{width} and height=#{height} order by id desc")
	public List<SFileThumb> queryThumbList(@Param("fileId")String fileId,@Param("width")int width,@Param("height")int height);
}
