/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  DataService.java   
 * @Package io.github.junxworks.ep.modules.modules.sys.service   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2020-3-2 14:30:34   
 * @version V1.0 
 * @Copyright: 2020 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.modules.modules.sys.service;

import java.util.List;
import java.util.Map;

public interface DataService {
	
	/**
	 * 获取sql模板
	 * if判断语句：{if(bisType)} and otherType=:bisType{endif}
	 * 			   {if(money>0)} and otherType=:bisType{endif}
	 * 
	 * @param uid the uid
	 * @param cond the cond
	 * @return sql template 属性
	 */
	public String getSqlTemplate(String uid, Map<String, String> cond);

	/**
	 * 根据入参和sql的uid，获得最终查询结果数据
	 *
	 * @param uid the uid
	 * @param cond the cond
	 * @return data by SQL uid 属性
	 * @throws Exception the exception
	 */
	List<Map<String, Object>> getDataBySQLUid(String uid, Map<String, String> cond)  throws Exception;
}
