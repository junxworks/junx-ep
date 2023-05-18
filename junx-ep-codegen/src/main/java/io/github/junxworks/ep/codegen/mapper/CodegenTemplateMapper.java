/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SJobMapper.java   
 * @Package io.github.junxworks.ep.scheduler.mapper   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 17:50:25   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.codegen.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.github.junxworks.ep.codegen.dto.EpCgTemplateCondition;
import io.github.junxworks.ep.codegen.entity.EpCgTemplate;
import io.github.junxworks.ep.codegen.vo.EpCgTemplateVo;
import io.github.junxworks.ep.core.orm.TBaseMapper;

@Mapper
public interface CodegenTemplateMapper extends TBaseMapper<EpCgTemplate> {
	List<EpCgTemplateVo> queryList(EpCgTemplateCondition condition);

	@Select("select * from ep_cg_template where tmp_id=#{tmpId} and status=0")
	EpCgTemplateVo queryTemplateByTmpId(@Param("tmpId") String tmpId);
}
