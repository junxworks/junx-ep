/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DataService.java   
 * @Package io.github.junxworks.ep.sys.service   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:17:48   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.service;

import java.util.List;
import java.util.Map;

/**
 * {类的详细说明}.
 *
 * @ClassName:  DataService
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
public interface DataService {
	
	/**
	 * 返回 sql template 属性.
	 *
	 * @param uid the uid
	 * @param cond the cond
	 * @return sql template 属性
	 */
	public String getSqlTemplate(String uid, Map<String, String> cond);

	/**
	 * 返回 data by SQL uid 属性.
	 *
	 * @param uid the uid
	 * @param cond the cond
	 * @return data by SQL uid 属性
	 * @throws Exception the exception
	 */
	List<Map<String, Object>> getDataBySQLUid(String uid, Map<String, String> cond)  throws Exception;
}
