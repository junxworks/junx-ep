/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  PageUtils.java   
 * @Package io.github.junxworks.ep.core.utils   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:37   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.utils;

import java.util.Map;

import com.github.pagehelper.PageHelper;
import io.github.junxworks.ep.core.Pageable;

import io.github.junxworks.junx.core.exception.BaseRuntimeException;

/**
 * {类的详细说明}.
 *
 * @ClassName:  PageUtils
 * @author: Michael
 * @date:   2020-7-19 12:18:37
 * @since:  v1.0
 */
public class PageUtils {
	public static void setPage(Object params) {
		Object pageNo = null;
		Object pageSize = null;
		if (params instanceof Pageable) {
			pageNo = ((Pageable) params).getPageNo();
			pageSize = ((Pageable) params).getPageSize();
		} else if (params instanceof Map) {
			pageNo = ((Map<?, ?>) params).get("pageNo");
			pageSize = ((Map<?, ?>) params).get("pageSize");
		}
		if (pageNo == null || pageSize == null) {
			throw new BaseRuntimeException("分页参数pageNo或者pageSize不能为空");
		}
		int pageNum = Integer.parseInt(pageNo.toString());
		int size = Integer.parseInt(pageSize.toString());
		PageHelper.startPage(pageNum < 1 ? 1 : pageNum, size == -1 ? Integer.MAX_VALUE : size);
	}
}
