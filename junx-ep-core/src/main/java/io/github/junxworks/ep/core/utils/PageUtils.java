package io.github.junxworks.ep.core.utils;

import java.util.Map;

import com.github.pagehelper.PageHelper;
import io.github.junxworks.ep.core.Pageable;

import io.github.junxworks.junx.core.exception.BaseRuntimeException;

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
		PageHelper.startPage(pageNum, size == -1 ? Integer.MAX_VALUE : size);
	}
}
