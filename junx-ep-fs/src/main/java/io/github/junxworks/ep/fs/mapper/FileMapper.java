package io.github.junxworks.ep.fs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.fs.entity.EpSFile;
import io.github.junxworks.ep.fs.entity.EpSFileThumb;

@Mapper
public interface FileMapper extends BaseMapper{
	@Select("select * from ep_s_file")
	public List<EpSFile> getAll();

	@Select("select * from ep_s_file where id=#{id}")
	public EpSFile findById(String id);

	@Select("select * from ep_s_file where file_group=#{fileGroup}")
	public List<EpSFile> findByGroup(String fileGroup);

	@Select("select * from ep_s_file where org_no=#{orgNo}")
	public List<EpSFile> findByOrg(String orgNo);

	@Select("delete from ep_s_file where id=#{id}")
	public void deleteById(String id);

	@Select("delete from ep_s_file where file_group=#{fileGroup}")
	public void deleteByGroup(String fileGroup);

	@Select("select * from ep_s_file_thumb where file_id=#{fileId} and width=#{width} and height=#{height} order by id desc")
	public List<EpSFileThumb> queryThumbList(@Param("fileId")String fileId,@Param("width")int width,@Param("height")int height);
}
