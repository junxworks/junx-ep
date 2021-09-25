/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  PvcContext.java   
 * @Package io.github.junxworks.ep.core.pvc   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-6-20 15:35:29   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.pvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.junxworks.ep.core.pvc.dao.PersistenceService;
import io.github.junxworks.junx.core.util.ClockUtils;
import io.github.junxworks.junx.core.util.ClockUtils.Clock;
import io.github.junxworks.junx.event.EventBus;

/**
 * 持久化版本控制上下文
 *
 * @ClassName:  PvcContext
 * @author: Michael
 * @date:   2021-6-20 13:37:15
 * @since:  v1.0
 */
public class PvcContext {

	private static final Logger log = LoggerFactory.getLogger(PvcContext.class);

	/** 模块pvc包路径. */
	private String pvcPath;

	/** 功能模块名，用于数据库profile表记录版本数据. */
	private String moduleName;

	/** 事件总线. */
	private EventBus eventBus;

	/** 升级目标版本号，由探测器决定版本号. */
	private int targetVersion;

	/** 持久化服务. */
	private PersistenceService persistenceService;

	private Clock clock = ClockUtils.createClock();

	/** 数据库产品名称小写. */
	private String dbProductNameLowerCase;

	public EventBus getEventBus() {
		return eventBus;
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public int getTargetVersion() {
		return targetVersion;
	}

	public void setTargetVersion(int targetVersion) {
		this.targetVersion = targetVersion;
	}

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

	public PersistenceService getPersistenceService() {
		return persistenceService;
	}

	public void setPersistenceService(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	public String getDbProductNameLowerCase() {
		return dbProductNameLowerCase;
	}

	public void setDbProductNameLowerCase(String dbProductNameLowerCase) {
		this.dbProductNameLowerCase = dbProductNameLowerCase;
	}

	public void log() {
		log.info("Complete modlue \"{}\" persistence version control work,cost " + clock.countMillis() + " millis.", moduleName);
	}

}
