package io.github.junxworks.ep.fs.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.fs.entity.SFile;
import io.github.junxworks.ep.fs.entity.SFileThumb;

@Mapper
public interface FSMapper extends BaseMapper{
	@Select("select * from sys_file")
	public List<SFile> getAll();

	@Insert("INSERT INTO sys_file(id,file_name,file_group,org_no,file_size,file_extension,storage_id,oraginal_name,storage_driver,create_time) VALUES(#{id}, #{fileName}, #{fileGroup},#{orgNo},#{fileSize},#{fileExtension}, #{storageId}, #{oraginalName}, #{storageDriver}, #{createTime})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void insert(SFile entity);

	@Select("select * from sys_file where id=#{id}")
	public SFile findById(Long id);

	@Select("select * from sys_file where file_group=#{fileGroup}")
	public List<SFile> findByGroup(String fileGroup);

	@Select("select * from sys_file where org_no=#{orgNo}")
	public List<SFile> findByOrg(String orgNo);

	@Select("delete from sys_file where id=#{id}")
	public void deleteById(Long id);

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
	public List<SFileThumb> queryThumbList(@Param("fileId")Long fileId,@Param("width")int width,@Param("height")int height);
}
