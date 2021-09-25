/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  PersistenceVersionController.java   
 * @Package io.github.junxworks.ep.core.pvc   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-6-20 13:39:53   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.pvc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import io.github.junxworks.ep.core.pvc.dao.PersistenceService;
import io.github.junxworks.ep.core.pvc.dao.PersistenceServiceImpl;
import io.github.junxworks.ep.core.pvc.detector.DefaultVersionDetector;
import io.github.junxworks.ep.core.pvc.detector.VersionDetector;
import io.github.junxworks.ep.core.pvc.handler.ProfileUpdateHandler;
import io.github.junxworks.ep.core.pvc.handler.VersionUpdateHandler;
import io.github.junxworks.ep.core.utils.DBUtils;
import io.github.junxworks.ep.core.utils.SpringContextUtils;
import io.github.junxworks.junx.core.exception.FatalException;
import io.github.junxworks.junx.core.lifecycle.Service;
import io.github.junxworks.junx.core.util.StringUtils;
import io.github.junxworks.junx.event.EventBus;
import io.github.junxworks.junx.event.impl.SimpleEventBus;
import io.github.junxworks.junx.event.impl.SimpleEventChannel;

/**
 * 持久化版本控制器，探测EP数据库版本与当前EP版本，自动升级对应的数据库表结构
 * 
 * 注意：所有模块的库表初始化或更新sql，都必须要支持幂等操作
 *
 * @ClassName:  PersistenceVersionController
 * @author: Michael
 * @date:   2021-6-20 13:29:20
 * @since:  v1.0
 */
public class PersistenceVersionController extends Service {

	/** 模块pvc包路径. */
	private String pvcPath;

	/** 功能模块名，用于数据库profile表记录版本数据. */
	private String moduleName;

	/** 版本探测器，支持自定义. */
	private VersionDetector versionDetector;

	/** 事件总线. */
	@Autowired(required = false)
	private EventBus eventBus;

	private PersistenceService persistenceService;

	public String getPvcPath() {
		return pvcPath;
	}

	public void setPvcPath(String pvcPath) {
		this.pvcPath = pvcPath;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public VersionDetector getVersionDetector() {
		return versionDetector;
	}

	public void setVersionDetector(VersionDetector versionDetector) {
		this.versionDetector = versionDetector;
	}

	/**
	 * Do start.
	 *
	 * @throws Throwable the throwable
	 */
	@Override
	protected void doStart() throws Throwable {
		DataSource ds = SpringContextUtils.getBean(DataSource.class);
		if (ds == null) {
			throw new FatalException("No DataSource object injected.");
		}
		if (StringUtils.isNull(moduleName)) {
			throw new FatalException("ModuleName can not be null.");
		}
		persistenceService = new PersistenceServiceImpl();
		persistenceService.setDataSource(ds);
		if (eventBus == null) {
			eventBus = new SimpleEventBus();
			((Service) eventBus).setName(moduleName + "_pvc_bus");
			eventBus.start();
		}
		SimpleEventChannel updateVersion = new SimpleEventChannel(moduleName + "_pvc_channel", moduleName + Topics.TOPIC_UPDATE_VERSION);
		updateVersion.setEventHandler(new VersionUpdateHandler());
		eventBus.registerChannel(updateVersion);

		SimpleEventChannel updateProfile = new SimpleEventChannel(moduleName + "_pvc_done_channel", moduleName + Topics.TOPIC_UPDATE_VERSION_DONE);
		updateProfile.setEventHandler(new ProfileUpdateHandler());
		eventBus.registerChannel(updateProfile);

		PvcContext ctx = new PvcContext();
		ctx.setEventBus(eventBus);
		ctx.setModuleName(moduleName);
		ctx.setPvcPath(pvcPath);
		ctx.setPersistenceService(persistenceService);
		ctx.setDbProductNameLowerCase(DBUtils.getDBProductName().toLowerCase());
		if (versionDetector == null) {
			versionDetector = new DefaultVersionDetector(ctx);
		}
		versionDetector.start();
	}

	/**
	 * Do stop.
	 *
	 * @throws Throwable the throwable
	 */
	@Override
	protected void doStop() throws Throwable {
		eventBus.stop();
		versionDetector.stop();
	}

}
