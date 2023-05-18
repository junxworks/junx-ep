/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  FsMode.java   
 * @Package io.github.junxworks.ep.core.fs   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2022年11月17日 上午11:37:25   
 * @version V1.0 
 * @Copyright: 2022 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.fs;

/**
 * {类的详细说明}.
 *
 * @ClassName:  FsMode
 * @author: Michael
 * @date:   2022年11月17日 上午11:37:25
 * @since:  v1.0
 */
public enum FsMode {

	/** oss. */
	OSS("oss", "io.github.junxworks.ep.fs.driver.OssRepositoryDriver"),
	/** local fs. */
	LOCAL_FS("local", "io.github.junxworks.ep.fs.driver.LocalFileSystemDriver");

	/** mode. */
	private String mode;

	/** driver. */
	private String driver;

	/**
	 * 构造一个新的 fs mode 对象.
	 *
	 * @param mode the mode
	 * @param driver the driver
	 */
	private FsMode(String mode, String driver) {
		this.mode = mode;
		this.driver = driver;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	/**
	 * 返回 enum 属性.
	 *
	 * @param mode the mode
	 * @return enum 属性
	 */
	public static FsMode getEnum(String mode) {
		for (FsMode m : FsMode.values()) {
			if (m.mode.equals(mode)) {
				return m;
			}
		}
		return null;
	}
}
