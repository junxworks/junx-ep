/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ProfileUpdateHandler.java   
 * @Package io.github.junxworks.ep.core.pvc.handler   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-6-20 16:09:07   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.pvc.handler;

import io.github.junxworks.ep.core.pvc.Params;
import io.github.junxworks.ep.core.pvc.PvcContext;
import io.github.junxworks.junx.event.EventChannel;
import io.github.junxworks.junx.event.EventChannelHandler;
import io.github.junxworks.junx.event.EventContext;

/**
 * 系统库表记录升级
 *
 * @ClassName:  ProfileUpdateHandler
 * @author: Michael
 * @date:   2021-6-20 16:09:07
 * @since:  v1.0
 */
public class ProfileUpdateHandler implements EventChannelHandler {

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
		ctx.getPersistenceService().setPV(ctx.getModuleName(), ctx.getTargetVersion());
	}

}
