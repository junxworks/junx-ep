/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DateFunc.java   
 * @Package io.github.junxworks.ep.qlexp.functions   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-3-1 21:18:48   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.qlexp.functions;

import java.util.Date;

import io.github.junxworks.junx.core.util.DateUtils;

/**
 * {类的详细说明}.
 *
 * @ClassName:  DateFunc
 * @author: Michael
 * @date:   2021-3-1 21:18:48
 * @since:  v1.0
 */
public class DateFunc {

	/**
	 * Date format.
	 *
	 * @param dateStr the date str
	 * @param inputFormat the input format
	 * @param outputFormat the output format
	 * @return the string
	 */

	public static String dateFormat(String dateStr, String inputFormat, String outputFormat) {
		return DateUtils.format(parseDate(dateStr, inputFormat), outputFormat);
	}

	/**
	 * Parses the date.
	 *
	 * @param dateStr the date str
	 * @param inputFormat the input format
	 * @return the date
	 */
	public static Date parseDate(String dateStr, String inputFormat) {
		return DateUtils.parse(dateStr, inputFormat);
	}

}
