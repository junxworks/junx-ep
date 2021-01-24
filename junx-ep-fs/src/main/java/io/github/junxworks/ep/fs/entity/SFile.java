/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  SysFileEntity.java   
 * @Package com.yrxd.filesvc.entity   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-9 16:37:39   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.fs.entity;

import java.util.Date;

import io.github.junxworks.ep.core.orm.annotations.Table;

/**
 * 文件服务实体类
 * 
 * @ClassName:  SysFileEntity
 * @author: 王兴
 * @date:   2019-1-9 16:37:39
 * @since:  v1.0
 */
@Table(tableName = "s_file", tableComment = "")
public class SFile {

	/** 系统自动生成的id. */
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
	private String fileExtension;

	/** 存储id. */
	private String storageId;

	/** 源文件名. */
	private String oraginalName;

	/** 存储驱动. */
	private String storageDriver;

	/** 创建时间 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
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

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
