package io.github.junxworks.ep.fs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.fs.entity.SFile;
import io.github.junxworks.ep.fs.entity.SFileThumb;

@Mapper
public interface FileMapper extends BaseMapper{
	@Select("select * from s_file")
	public List<SFile> getAll();

	@Select("select * from s_file where id=#{id}")
	public SFile findById(String id);

	@Select("select * from s_file where file_group=#{fileGroup}")
	public List<SFile> findByGroup(String fileGroup);

	@Select("select * from s_file where org_no=#{orgNo}")
	public List<SFile> findByOrg(String orgNo);

	@Select("delete from s_file where id=#{id}")
	public void deleteById(String id);

	@Select("delete from s_file where file_group=#{fileGroup}")
	public void deleteByGroup(String fileGroup);

	@Select("select * from s_file_thumb where fileId=#{fileId} and width=#{width} and height=#{height} order by id desc")
	public List<SFileThumb> queryThumbList(@Param("fileId")String fileId,@Param("width")int width,@Param("height")int height);
}
