/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  OrgService.java   
 * @Package io.github.junxworks.ep.sys.service   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:41   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.service;

import java.util.List;

import io.github.junxworks.ep.sys.dto.OrgDto;
import io.github.junxworks.ep.sys.vo.OrgVo;
import io.github.junxworks.ep.sys.vo.TreeNodeVo;
import io.github.junxworks.ep.sys.vo.TreeSelectVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  OrgService
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
public interface OrgService {
	
	/**
	 * Query org list.
	 *
	 * @param dto the dto
	 * @return the list
	 */
	List<OrgVo> queryOrgList(OrgDto dto);

	/**
	 * Query org by id.
	 *
	 * @param id the id
	 * @return the org vo
	 */
	OrgVo queryOrgById(Long id);

	/**
	 * Save org.
	 *
	 * @param dto the dto
	 * @return the int
	 */
	int saveOrg(OrgDto dto);

	/**
	 * Delete org.
	 *
	 * @param id the id
	 * @return the int
	 */
	int deleteOrg(Long id);

	/**
	 * Update org.
	 *
	 * @param dto the dto
	 * @return the int
	 */
	int updateOrg(OrgDto dto);

	/**
	 * Query org tree.
	 *
	 * @param rootNo the root no
	 * @return the list
	 */
	List<TreeNodeVo> queryOrgTree(String rootNo);

	/**
	 * Query tree select.
	 *
	 * @param rootNo the root no
	 * @return the list
	 */
	List<TreeSelectVo> queryTreeSelect(String rootNo);
}
