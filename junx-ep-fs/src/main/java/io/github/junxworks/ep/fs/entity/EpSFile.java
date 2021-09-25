package io.github.junxworks.ep.fs.entity;

import io.github.junxworks.ep.core.orm.annotations.Table;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Column;
import java.util.Date;

/**
 * <p>Entity Class</p>
 * <p>Table: ep_s_file</p>
 *
 * @since 2021-2-19 15:46:15 Generated by JunxPlugin
 */
 
@Table(tableName="ep_s_file",tableComment="")
public class EpSFile {
	
    @PrimaryKey
    @Column(name="id", type="VARCHAR", length="50", nullable="false", comment="主键")
    private String id;
    
    @Column(name="file_name", type="VARCHAR", length="100", nullable="false", comment="上传文件名，可人为指定")
    private String fileName;	
    
    @Column(name="file_group", type="VARCHAR", length="50", nullable="false", comment="文件分组，默认为default")
    private String fileGroup;	
    
    @Column(name="org_no", type="VARCHAR", length="50", nullable="false", comment="组织编号，由于文件服务是公用的，加上组织编号可以区分文件是属于哪个组织")
    private String orgNo;	
    
    @Column(name="file_size", type="INTEGER", length="10", nullable="false", comment="文件大小")
    private Integer fileSize;	
    
    @Column(name="file_ext", type="VARCHAR", length="20", nullable="true", comment="文件扩展名")
    private String fileExt;	
    
    @Column(name="storage_id", type="VARCHAR", length="100", nullable="false", comment="文件存储id，通过这个id取获取")
    private String storageId;	
    
    @Column(name="oraginal_name", type="VARCHAR", length="300", nullable="false", comment="文件原名")
    private String oraginalName;	
    
    @Column(name="storage_driver", type="VARCHAR", length="100", nullable="false", comment="存储驱动名，后台程序记录，可用于问题分析")
    private String storageDriver;	
    
    @Column(name="create_time", type="TIMESTAMP", length="19", nullable="false", comment="创建时间")
    private Date createTime;	
    

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getFileName(){
        return this.fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }
    public String getFileGroup(){
        return this.fileGroup;
    }

    public void setFileGroup(String fileGroup){
        this.fileGroup = fileGroup;
    }
    public String getOrgNo(){
        return this.orgNo;
    }

    public void setOrgNo(String orgNo){
        this.orgNo = orgNo;
    }
    public Integer getFileSize(){
        return this.fileSize;
    }

    public void setFileSize(Integer fileSize){
        this.fileSize = fileSize;
    }
    public String getFileExt(){
        return this.fileExt;
    }

    public void setFileExt(String fileExt){
        this.fileExt = fileExt;
    }
    public String getStorageId(){
        return this.storageId;
    }

    public void setStorageId(String storageId){
        this.storageId = storageId;
    }
    public String getOraginalName(){
        return this.oraginalName;
    }

    public void setOraginalName(String oraginalName){
        this.oraginalName = oraginalName;
    }
    public String getStorageDriver(){
        return this.storageDriver;
    }

    public void setStorageDriver(String storageDriver){
        this.storageDriver = storageDriver;
    }
    public Date getCreateTime(){
        return this.createTime;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
}