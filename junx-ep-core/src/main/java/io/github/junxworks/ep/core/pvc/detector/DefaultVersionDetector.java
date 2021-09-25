/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DefaultVersionDetector.java   
 * @Package io.github.junxworks.ep.core.pvc.detector   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-6-20 13:51:52   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.pvc.detector;

import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import io.github.junxworks.ep.core.pvc.Params;
import io.github.junxworks.ep.core.pvc.PvcContext;
import io.github.junxworks.ep.core.pvc.Topics;
import io.github.junxworks.ep.core.pvc.dao.PersistenceService;
import io.github.junxworks.junx.core.exception.FatalException;
import io.github.junxworks.junx.core.lifecycle.Service;
import io.github.junxworks.junx.core.util.StringUtils;
import io.github.junxworks.junx.event.EventBus;
import io.github.junxworks.junx.event.EventContext;

public class DefaultVersionDetector extends Service implements VersionDetector {

	/** 当前版本. */
	private int currentVersion;

	/** 最新版本. */
	private int lastVersion;

	private PvcContext ctx;

	public DefaultVersionDetector(PvcContext ctx) {
		this.ctx = ctx;
	}

	@Override
	protected void doStart() throws Throwable {
		String pvcPath = ctx.getPvcPath();
		Resource profile = new ClassPathResource(pvcPath + "/" + PROFILE_NAME);
		if (!profile.exists()) {
			throw new FatalException("PVC profile in directory \"%s\" does not exist.", pvcPath);
		}
		Properties p = PropertiesLoaderUtils.loadProperties(profile);
		String version = p.getProperty(PROPERTY_NAME);
		if (StringUtils.isNull(version)) {
			throw new FatalException("Incorrect version definition in PVC profile in resource \"%s\".", profile.getURI().toString());
		}
		lastVersion = Integer.valueOf(version);
		PersistenceService ps = ctx.getPersistenceService();
		try {
			currentVersion = ps.getCurrentPVByModuleName(ctx.getModuleName());
		} catch (Throwable e) {
			throw new FatalException(e);
		}
		if (currentVersion < 0) {
			currentVersion = -1;
		}
		String moduleName = ctx.getModuleName();
		EventBus bus = ctx.getEventBus();
		try {
			while (currentVersion < lastVersion) {
				ctx.setTargetVersion(++currentVersion);
				EventContext event = new EventContext(moduleName + Topics.TOPIC_UPDATE_VERSION);
				event.setData(Params.PARAM_PVC_CONTEXT, ctx);
				bus.publish(event);
				event = new EventContext(moduleName + Topics.TOPIC_UPDATE_VERSION_DONE);
				event.setData(Params.PARAM_PVC_CONTEXT, ctx);
				bus.publish(event);
			}
		} catch (Throwable t) {
			throw new FatalException(t);
		}
		ctx.log();
	}

	@Override
	protected void doStop() throws Throwable {

	}

}
