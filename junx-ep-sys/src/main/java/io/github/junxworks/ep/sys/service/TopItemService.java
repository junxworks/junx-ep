/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  UserService.java   
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

import io.github.junxworks.ep.sys.dto.TopItemConditionDto;
import io.github.junxworks.ep.sys.dto.TopItemDto;
import io.github.junxworks.ep.sys.entity.EpSTopItem;
import io.github.junxworks.ep.sys.vo.TopItemVo;
// TODO: Auto-generated Javadoc

/**
 * The Interface TopItemService.
 */
public interface TopItemService {
	List<TopItemVo> queryItemListByCondition(TopItemConditionDto cond);

	EpSTopItem queryItemById(Long id);

	int saveItem(TopItemDto item);

}
