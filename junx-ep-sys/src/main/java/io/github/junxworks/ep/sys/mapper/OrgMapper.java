/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  OrgMapper.java   
 * @Package io.github.junxworks.ep.sys.mapper   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:42   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.sys.dto.OrgDto;
import io.github.junxworks.ep.sys.vo.OrgVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  OrgMapper
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@Mapper
public interface OrgMapper extends BaseMapper {

	/**
	 * Query org list.
	 *
	 * @param dto the dto
	 * @return the list
	 */
	@Select("select * from s_org where status=0")
	List<OrgVo> queryOrgList(OrgDto dto);

	/**
	 * Select by id.
	 *
	 * @param id the id
	 * @return the org vo
	 */
	@Select("select s.*,ss.org_name `parentName` from s_org s left join s_org ss on s.parent_no=ss.org_no and ss.status=0 where s.id=#{id} and s.status=0")
	OrgVo selectById(Long id);

	/**
	 * Select by org no.
	 *
	 * @param orgNo the org no
	 * @return the org vo
	 */
	@Select("select * from s_org where org_no=#{orgNo} and status=0")
	OrgVo selectByOrgNo(String orgNo);

	/**
	 * Delete by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Delete("update s_org set status=-1 where id=#{id}")
	int deleteById(Long id);

	/**
	 * Update sub parent no.
	 *
	 * @param oldNo the old no
	 * @param newNo the new no
	 * @return the int
	 */
	@Update("update s_org set parent_no=#{newNo} where parent_no=#{oldNo}")
	int updateSubParentNo(@Param("oldNo") String oldNo, @Param("newNo") String newNo);

	/**
	 * Query children by org no.
	 *
	 * @param parentNo the parent no
	 * @return the list
	 */
	@Select("select * from s_org where parent_no=#{parentNo} and status=0")
	List<OrgVo> queryChildrenByOrgNo(@Param("parentNo") String parentNo);

	/**
	 * Query children by id.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Select("select * from s_org where parent_no=(select orgNo from s_org where id=#{id}) and status=0")
	List<OrgVo> queryChildrenById(@Param("id") Long id);
}