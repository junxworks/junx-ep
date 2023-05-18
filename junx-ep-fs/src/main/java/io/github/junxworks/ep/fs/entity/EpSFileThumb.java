package io.github.junxworks.ep.fs.entity;

import io.github.junxworks.ep.core.orm.annotations.Table;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Column;
import java.util.Date;

/**
 * <p>Entity Class</p>
 * <p>Table: ep_s_file_thumb</p>
 *
 * @since 2021-2-19 15:46:15 Generated by JunxPlugin
 */

@Table(tableName = "ep_s_file_thumb", tableComment = "")
public class EpSFileThumb {

	@PrimaryKey
	@Column(name = "id", type = "BIGINT", length = "20", nullable = "false", comment = "主键")
	private Long id;

	@Column(name = "file_id", type = "BIGINT", length = "20", nullable = "false", comment = "")
	private Long fileId;

	@Column(name = "width", type = "INTEGER", length = "10", nullable = "false", comment = "")
	private Integer width;

	@Column(name = "height", type = "INTEGER", length = "10", nullable = "false", comment = "")
	private Integer height;

	@Column(name = "file_size", type = "BIGINT", length = "19", nullable = "false", comment = "文件大小")
	private Long fileSize;

	@Column(name = "file_ext", type = "VARCHAR", length = "20", nullable = "true", comment = "文件扩展名")
	private String fileExt;

	@Column(name = "storage_id", type = "VARCHAR", length = "100", nullable = "false", comment = "文件存储id，通过这个id取获取")
	private String storageId;

	@Column(name = "storage_driver", type = "VARCHAR", length = "100", nullable = "false", comment = "存储驱动名，后台程序记录，可用于问题分析")
	private String storageDriver;

	@Column(name = "create_time", type = "TIMESTAMP", length = "19", nullable = "false", comment = "创建时间")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileExt() {
		return this.fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getStorageId() {
		return this.storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getStorageDriver() {
		return this.storageDriver;
	}

	public void setStorageDriver(String storageDriver) {
		this.storageDriver = storageDriver;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}