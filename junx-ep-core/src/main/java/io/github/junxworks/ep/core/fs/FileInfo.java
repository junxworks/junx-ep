/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  FileInfo.java   
 * @Package io.github.junxworks.ep.core.fs   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-6-5 16:55:43   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.fs;

import java.util.Date;

/**
 * 文件存储对象
 *
 * @ClassName:  FileInfo
 * @author: 王兴
 * @date:   2019-2-21 19:32:18
 * @since:  v1.0
 */
public class FileInfo {
	/** id. */
	private Long id;

	/** 指定文件名. */
	private String fileName;

	/** 文件分组. */
	private String fileGroup;

	/** 组织编号，由于文件服务是公用的，加上组织编号可以区分文件是属于哪个组织. */
	private String orgNo;

	/** 文件大小. */
	private Integer fileSize;

	/** 文件扩展名. */
	private String fileExt;

	/** 存储id. */
	private String storageId;

	/** 源文件名. */
	private String oraginalName;

	/** 存储驱动. */
	private String storageDriver;

	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileGroup() {
		return fileGroup;
	}

	public void setFileGroup(String fileGroup) {
		this.fileGroup = fileGroup;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getOraginalName() {
		return oraginalName;
	}

	public void setOraginalName(String oraginalName) {
		this.oraginalName = oraginalName;
	}

	public String getStorageDriver() {
		return storageDriver;
	}

	public void setStorageDriver(String storageDriver) {
		this.storageDriver = storageDriver;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
