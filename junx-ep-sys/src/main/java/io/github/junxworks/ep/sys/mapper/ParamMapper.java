/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ParamMapper.java   
 * @Package io.github.junxworks.ep.sys.mapper   
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
package io.github.junxworks.ep.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.github.junxworks.ep.core.orm.BaseMapper;
import io.github.junxworks.ep.sys.entity.SParam;

/**
 * <p>Mapper</p>
 * <p>Table: s_dict</p>
 *
 * @since 2019-6-28 15:44:30 Generated by JunxPlugin
 */
@Mapper
public interface ParamMapper extends BaseMapper {
	@Select("select * from s_param where status=0 and paramName=#{paramName} and paramGroup=#{group}")
	SParam getParam(@Param("group") String group, @Param("paramName") String paramName);

	@Update("update s_param set paramValue=#{paramValue} where status=0 and paramName=#{paramName} and paramGroup=#{group}")
	int updateParam(@Param("group") String group, @Param("paramName") String paramName, @Param("paramValue") String paramValue);
}