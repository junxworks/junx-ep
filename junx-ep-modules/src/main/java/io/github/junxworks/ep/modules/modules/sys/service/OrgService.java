package io.github.junxworks.ep.modules.modules.sys.service;

import java.util.List;

import io.github.junxworks.ep.modules.modules.sys.dto.OrgDto;
import io.github.junxworks.ep.modules.modules.sys.vo.OrgVo;
import io.github.junxworks.ep.modules.modules.sys.vo.TreeNodeVo;
import io.github.junxworks.ep.modules.modules.sys.vo.TreeSelectVo;

public interface OrgService {
	List<OrgVo> queryOrgList(OrgDto dto);

	OrgVo queryOrgById(Long id);

	int saveOrg(OrgDto dto);

	int deleteOrg(Long id);

	int updateOrg(OrgDto dto);

	List<TreeNodeVo> queryOrgTree(String rootNo);

	List<TreeSelectVo> queryTreeSelect(String rootNo);
}
