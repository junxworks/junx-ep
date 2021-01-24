/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  AbstractFileRepository.java   
 * @Package io.github.junxworks.ep.fs.driver   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-8-2 13:58:55   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.fs.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.UUID;

import io.github.junxworks.junx.core.lifecycle.Service;
import io.github.junxworks.junx.core.util.StringUtils;

/**
 * 默认文件仓库实现类
 *
 * @ClassName:  DefaultFileRepository
 * @author: 王兴
 * @date:   2019-1-6 11:38:43
 * @since:  v1.0
 */
public abstract class AbstractFileRepository extends Service implements FileRepository {

	protected void intputStreamToOutputStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[8 * 1024];
		int readBytes;
		while ((readBytes = in.read(buffer)) != -1) {
			out.write(buffer, 0, readBytes);
		}
	}

	/**
	 * 生成一个新的文件token
	 *
	 * @return the string
	 */
	protected String createToken() {
		String uuid = UUID.randomUUID().toString();//唯一标识
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int mon = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return StringUtils.join(new Object[] { year, mon, day, uuid }, "/");
	}
}
