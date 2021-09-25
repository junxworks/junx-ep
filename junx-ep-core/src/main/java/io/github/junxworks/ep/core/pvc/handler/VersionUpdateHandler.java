/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  VersionUpdateHandler.java   
 * @Package io.github.junxworks.ep.core.pvc.handler   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-6-20 16:08:34   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.pvc.handler;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import io.github.junxworks.ep.core.pvc.Params;
import io.github.junxworks.ep.core.pvc.PvcContext;
import io.github.junxworks.junx.core.util.ByteUtils;
import io.github.junxworks.junx.core.util.StringUtils;
import io.github.junxworks.junx.event.EventChannel;
import io.github.junxworks.junx.event.EventChannelHandler;
import io.github.junxworks.junx.event.EventContext;

/**
 * 功能模块表结构升级
 *
 * @ClassName:  VersionUpdateHandler
 * @author: Michael
 * @date:   2021-6-20 16:08:34
 * @since:  v1.0
 */
public class VersionUpdateHandler implements EventChannelHandler {

	/**
	 * Handle event.
	 *
	 * @param event the event
	 * @param channel the channel
	 * @throws Exception the exception
	 */
	@Override
	public void handleEvent(EventContext event, EventChannel channel) throws Exception {
		PvcContext ctx = event.getData(PvcContext.class, Params.PARAM_PVC_CONTEXT);
		String targetVersion = String.valueOf(ctx.getTargetVersion());
		String pvPath = ctx.getPvcPath();
		String dbpn = ctx.getDbProductNameLowerCase();
		String scriptPath = StringUtils.join("/", pvPath, dbpn, targetVersion);
		Resource script = new ClassPathResource(scriptPath);
		String s = new String(ByteUtils.inputStreamToBytes(script.getInputStream()));
		ctx.getPersistenceService().executeSql(s);
	}

}
