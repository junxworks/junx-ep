/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DataMapper.java   
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

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import io.github.junxworks.ep.core.orm.TBaseMapper;
import io.github.junxworks.ep.sys.dto.TopItemConditionDto;
import io.github.junxworks.ep.sys.entity.EpSTopItem;
import io.github.junxworks.ep.sys.vo.TopItemVo;

@Mapper
public interface TopItemMapper extends TBaseMapper<EpSTopItem> {
	
	/**
	 * 返回 sql by uid 属性.
	 *
	 * @param uid the uid
	 * @return sql by uid 属性
	 */
	@Select("<script>" +
			"SELECT i.*, u.name `create_user_name` " + 
			"FROM ep_s_top_item i " + 
			"     INNER JOIN ep_s_user u ON u.create_user = u.id " + 
			" where i.status!=-1 "+ 
			"<if test='itemName!=null and itemName.length>0'> " +
			"     and i.item_name like CONCAT('%',#{itemName},'%') " +
			"</if>" +
			"<if test='status!=null'> " +
			"     and i.status=#{status} " +
			"</if>" + 
			" order by i.item_index asc,i.id desc"+
			"</script>")
	List<TopItemVo> queryItemListByCondition(TopItemConditionDto cond);
}