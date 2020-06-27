package io.github.junxworks.ep.modules.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.modules.modules.sys.dto.OrgDto;
import io.github.junxworks.ep.modules.modules.sys.vo.OrgVo;

@Mapper
public interface OrgMapper extends BaseMapper {

	@Select("select * from s_org where status=0")
	List<OrgVo> queryOrgList(OrgDto dto);

	@Select("select s.*,ss.orgName `parentName` from s_org s left join s_org ss on s.parentNo=ss.orgNo and ss.status=0 where s.id=#{id} and s.status=0")
	OrgVo selectById(Long id);

	@Select("select * from s_org where orgNo=#{orgNo} and status=0")
	OrgVo selectByOrgNo(String orgNo);

	@Delete("update s_org set status=-1 where id=#{id}")
	int deleteById(Long id);

	@Update("update s_org set parentNo=#{newNo} where parentNo=#{oldNo}")
	int updateSubParentNo(@Param("oldNo") String oldNo, @Param("newNo") String newNo);

	@Select("select * from s_org where parentNo=#{parentNo} and status=0")
	List<OrgVo> queryChildrenByOrgNo(@Param("parentNo") String parentNo);

	@Select("select * from s_org where parentNo=(select orgNo from s_org where id=#{id}) and status=0")
	List<OrgVo> queryChildrenById(@Param("id") Long id);
}