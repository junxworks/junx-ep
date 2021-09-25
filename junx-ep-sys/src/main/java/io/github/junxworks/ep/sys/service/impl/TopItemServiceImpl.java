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
package io.github.junxworks.ep.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.junxworks.ep.auth.model.UserModel;
import io.github.junxworks.ep.sys.dto.TopItemConditionDto;
import io.github.junxworks.ep.sys.dto.TopItemDto;
import io.github.junxworks.ep.sys.entity.EpSTopItem;
import io.github.junxworks.ep.sys.mapper.TopItemMapper;
import io.github.junxworks.ep.sys.service.TopItemService;
import io.github.junxworks.ep.sys.vo.TopItemVo;

/**
 * The Interface TopItemService.
 */
@Service("JunxEPTopItemService")
public class TopItemServiceImpl implements TopItemService {

	@Autowired
	private TopItemMapper topItemMapper;

	@Override
	public List<TopItemVo> queryItemListByCondition(TopItemConditionDto cond) {
		return topItemMapper.queryItemListByCondition(cond);
	}

	@Override
	public int saveItem(TopItemDto item) {
		EpSTopItem i = new EpSTopItem();
		BeanUtils.copyProperties(item, i);
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		if (i.getId() == null) {
			i.setCreateDate(new Date());
			i.setCreateUser(user.getId());
			return topItemMapper.insertWithoutNull(i);
		} else {
			i.setUpdateDate(new Date());
			i.setUpdateUser(user.getId());
			return topItemMapper.updateWithoutNull(i);
		}
	}

	@Override
	public EpSTopItem queryItemById(Long id) {
		// TODO Auto-generated method stub
		return topItemMapper.selectEntityByID(EpSTopItem.class, id);
	}

}
