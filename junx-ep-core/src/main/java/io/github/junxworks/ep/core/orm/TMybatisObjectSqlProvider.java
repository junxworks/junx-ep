/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  MybatisObjectSqlProvider.java   
 * @Package io.github.junxworks.ep.core.orm   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:36   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.orm;

import java.util.Map;

/**
 * {类的详细说明}.
 *
 * @ClassName:  MybatisObjectSqlProvider
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
public class TMybatisObjectSqlProvider<T> extends MybatisObjectSqlProvider {

	@SuppressWarnings("rawtypes")
	public String getOneByID(Map params) throws Exception {
		return SqlGenerator.getOneSQL((Class) params.get("class"), Long.valueOf(params.get("id").toString()));
	}

}
