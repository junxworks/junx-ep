/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  Condition.java   
 * @Package io.github.junxworks.ep.core.mybatis   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-9-2 10:45:10   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.orm;

/**
 * sql条件
 *
 * @ClassName:  Condition
 * @author: 王兴
 * @date:   2019-9-2 10:45:10
 * @since:  v1.0
 */
public class CompareOperators {

	/** 常量 GRATER_THAN.大于 */
	public static final String GRATER = ">";

	/** 常量 GRATER_EQUAL_THAN.大于等于 */
	public static final String GRATER_EQUAL = ">=";

	/** 常量 EQUAL.等于 */
	public static final String EQUAL = "=";

	/** 常量 EQUAL.不等于 */
	public static final String NOT_EQUAL = "!=";

	/** 常量 EQUAL.小于 */
	public static final String LESS = "<";

	/** 常量 GRATER_EQUAL_THAN.小于等于 */
	public static final String LESS_EQUAL = "<=";

	/** 常量 GRATER_EQUAL_THAN.全模糊匹配 */
	public static final String LIKE = "like";

	/** 常量 GRATER_EQUAL_THAN.右模糊匹配 */
	public static final String LIKE_R = "liker";

	/** 常量 GRATER_EQUAL_THAN.左模糊匹配 */
	public static final String LIKE_L = "likel";

	/** 常量 GRATER_EQUAL_THAN.IN */
	public static final String IN = "in";
}
