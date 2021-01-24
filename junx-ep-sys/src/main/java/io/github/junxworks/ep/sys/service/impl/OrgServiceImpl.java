/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  OrgServiceImpl.java   
 * @Package io.github.junxworks.ep.sys.service.impl   
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
package io.github.junxworks.ep.sys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.junxworks.ep.auth.model.UserModel;
import io.github.junxworks.ep.core.exception.BusinessException;
import io.github.junxworks.ep.sys.constants.RecordStatus;
import io.github.junxworks.ep.sys.dto.OrgDto;
import io.github.junxworks.ep.sys.entity.SOrg;
import io.github.junxworks.ep.sys.mapper.OrgMapper;
import io.github.junxworks.ep.sys.service.OrgService;
import io.github.junxworks.ep.sys.vo.OrgVo;
import io.github.junxworks.ep.sys.vo.TreeNodeVo;
import io.github.junxworks.ep.sys.vo.TreeSelectVo;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName:  OrgServiceImpl
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@Service
public class OrgServiceImpl implements OrgService {

	/** 常量 ROOT_ORG. */
	private static final String ROOT_ORG = "0";

	/** 常量 PATH_SEPARATER. */
	private static final String PATH_SEPARATER = ",";

	/** org mapper. */
	@Autowired
	private OrgMapper orgMapper;

	/**
	 * Query org list.
	 *
	 * @param dto the dto
	 * @return the list
	 */
	@Override
	public List<OrgVo> queryOrgList(OrgDto dto) {
		return orgMapper.queryOrgList(dto);
	}

	/**
	 * Query org by id.
	 *
	 * @param id the id
	 * @return the org vo
	 */
	@Override
	public OrgVo queryOrgById(Long id) {
		return orgMapper.selectById(id);
	}

	/**
	 * Save org.
	 *
	 * @param dto the dto
	 * @return the int
	 */
	@Override
	public int saveOrg(OrgDto dto) {
		if (StringUtils.isNull(dto.getParentNo())) {
			dto.setParentNo(ROOT_ORG);
		}
		OrgVo exists = orgMapper.selectByOrgNo(dto.getOrgNo());
		if (exists != null) {
			throw new BusinessException("组织[" + exists.getOrgName() + "]已经使用了组织编码\"" + dto.getOrgNo() + "\"");
		}
		SOrg org = new SOrg();
		BeanUtils.copyProperties(dto, org);
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		org.setCreateDate(new Date());
		org.setCreatorId(user.getId());
		org.setStatus(RecordStatus.NORMAL.getValue());
		//设置组织路径和顶级组织
		setOrgPath(org);
		return orgMapper.insertWithoutNull(org);
	}

	/**
	 * Query parents.
	 *
	 * @param orgNo the org no
	 * @return the org vo
	 */
	public OrgVo queryParents(String orgNo) {
		OrgVo p = null;
		if (StringUtils.notNull(orgNo)) {
			p = orgMapper.selectByOrgNo(orgNo);
			if (p != null) {
				p.setParent(queryParents(p.getParentNo()));
			}
		}
		return p;
	}

	/**
	 * Update org.
	 *
	 * @param dto the dto
	 * @return the int
	 */
	@Override
	@Transactional
	public int updateOrg(OrgDto dto) {
		OrgVo exists = orgMapper.selectByOrgNo(dto.getOrgNo());
		if (exists != null && !exists.getId().equals(dto.getId())) {
			throw new BusinessException("组织[" + exists.getOrgName() + "]已经使用了组织编码\"" + dto.getOrgNo() + "\"");
		}
		OrgVo oldOne = orgMapper.selectById(dto.getId());
		if (dto.getOrgNo().equals(dto.getParentNo())) {
			throw new BusinessException("不能选择自己做为上级组织");
		}
		SOrg org = new SOrg();
		BeanUtils.copyProperties(dto, org);
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		org.setModifyDate(new Date());
		org.setModifierId(user.getId());
		//设置组织路径和顶级组织
		setOrgPath(org);
		int count = orgMapper.updateWithoutNull(org);
		if (!oldOne.getOrgNo().equals(dto.getOrgNo()) || !oldOne.getParentNo().equals(dto.getParentNo())) {//修改了组织编码或者上级组织编码
			//修改组织编码,级联修改下级组织的上级组织编码
			updateChildrenOrgPath(oldOne.getOrgNo(), dto.getOrgNo());
		}
		return count;
	}

	/**
	 * Update children org path.
	 *
	 * @param oldNo the old no
	 * @param newNo the new no
	 */
	private void updateChildrenOrgPath(String oldNo, String newNo) {
		if (!oldNo.equals(newNo)) {
			orgMapper.updateSubParentNo(oldNo, newNo);
		}
		List<OrgVo> children = orgMapper.queryChildrenByOrgNo(newNo);
		children.forEach(o -> {
			SOrg org = new SOrg();
			BeanUtils.copyProperties(o, org);
			setOrgPath(org);
			orgMapper.updateWithoutNull(org); //更新orgPath
			updateChildrenOrgPath(org.getOrgNo(), org.getOrgNo());
		});
	}

	private void setOrgPath(SOrg org) {
		if (StringUtils.notNull(org.getParentNo()) && !ROOT_ORG.equals(org.getParentNo())) {
			OrgVo parent = queryParents(org.getParentNo());
			if (parent == null) {
				throw new BusinessException("上级组织编码\"" + org.getParentNo() + "\"不可用");
			}
			StringBuilder sb = new StringBuilder(parent.getOrgNo() + PATH_SEPARATER + org.getOrgNo());
			org.setTopLevelNo(parent.getOrgNo());
			while (parent.getParent() != null) {
				parent = parent.getParent();
				if (org.getOrgNo().equals(parent.getOrgNo())) {
					throw new BusinessException("不能选择自己的下级组织做上级组织");
				}
				sb.insert(0, parent.getOrgNo() + PATH_SEPARATER);
				org.setTopLevelNo(parent.getOrgNo());
			}
			org.setOrgPath(sb.toString());
		}
		String orgPath = org.getOrgPath();
		if (StringUtils.notNull(orgPath)) {
			org.setOrgPath(ROOT_ORG + PATH_SEPARATER + orgPath);
		} else {
			org.setOrgPath(ROOT_ORG + PATH_SEPARATER + org.getOrgNo());
		}
	}

	/**
	 * Query org tree.
	 *
	 * @param rootNo the root no
	 * @return the list
	 */
	@Override
	public List<TreeNodeVo> queryOrgTree(String rootNo) {
		List<OrgVo> orgs = orgMapper.queryOrgList(new OrgDto());
		List<TreeNodeVo> treeNodes = orgs.stream().flatMap(o -> {
			TreeNodeVo node = new TreeNodeVo();
			node.setId(o.getOrgNo());
			node.setTitle(o.getOrgName());
			node.setParentId(o.getParentNo());
			return Stream.of(node);
		}).collect(Collectors.toList());
		Map<String, TreeNodeVo> orgMap = treeNodes.stream().collect(Collectors.toMap(TreeNodeVo::getId, v -> v));
		treeNodes.forEach(v -> {
			if (v.getParentId() != null && !ROOT_ORG.equals(v.getParentId())) {
				orgMap.get(v.getParentId()).addChildren(v);
			}
		});
		return treeNodes.stream().filter(t -> {
			return ROOT_ORG.equals(t.getParentId());
		}).collect(Collectors.toList());
	}

	/**
	 * Delete org.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteOrg(Long id) {
		if (!orgMapper.queryChildrenById(id).isEmpty()) {
			throw new BusinessException("存在有效的下级组织，不能删除");
		}
		SOrg org = new SOrg();
		org.setId(id);
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		org.setModifyDate(new Date());
		org.setModifierId(user.getId());
		org.setStatus(RecordStatus.DELETED.getValue());
		return orgMapper.updateWithoutNull(org);
	}

	/**
	 * Query tree select.
	 *
	 * @param rootNo the root no
	 * @return the list
	 */
	@Override
	public List<TreeSelectVo> queryTreeSelect(String rootNo) {
		List<OrgVo> orgs = orgMapper.queryOrgList(new OrgDto());
		List<TreeSelectVo> treeNodes = orgs.stream().flatMap(o -> {
			TreeSelectVo node = new TreeSelectVo();
			node.setId(o.getOrgNo());
			node.setName(o.getOrgName());
			node.setOpen(true);
			node.setParentId(o.getParentNo());
			return Stream.of(node);
		}).collect(Collectors.toList());
		Map<String, TreeSelectVo> orgMap = treeNodes.stream().collect(Collectors.toMap(TreeSelectVo::getId, v -> v));
		treeNodes.forEach(v -> {
			if (v.getParentId() != null && !ROOT_ORG.equals(v.getParentId())) {
				orgMap.get(v.getParentId()).addChild(v);
			}
		});
		return treeNodes.stream().filter(t -> {
			return ROOT_ORG.equals(t.getParentId());
		}).collect(Collectors.toList());
	}

}
