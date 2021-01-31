/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SFile.java   
 * @Package io.github.junxworks.ep.fs.entity   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-31 17:27:19   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.fs.entity;

import io.github.junxworks.ep.core.orm.annotations.Table;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Column;
import java.util.Date;

/**
 * {类的详细说明}.
 *
 * @ClassName:  SFile
 * @author: Michael
 * @date:   2021-1-31 17:27:19
 * @since:  v1.0
 */
 
@Table(tableName="s_file",tableComment="")
public class SFile {
	
    /** id. */
    @PrimaryKey
    @Column(name="id", type="VARCHAR", length="50", nullable="false", comment="主键")
    private String id;
    
    /** file name. */
    @Column(name="fileName", type="VARCHAR", length="100", nullable="false", comment="上传文件名，可人为指定")
    private String fileName;	
    
    /** file group. */
    @Column(name="fileGroup", type="VARCHAR", length="50", nullable="false", comment="文件分组，默认为default")
    private String fileGroup;	
    
    /** org no. */
    @Column(name="orgNo", type="VARCHAR", length="50", nullable="false", comment="组织编号，由于文件服务是公用的，加上组织编号可以区分文件是属于哪个组织")
    private String orgNo;	
    
    /** file size. */
    @Column(name="fileSize", type="INTEGER", length="10", nullable="false", comment="文件大小")
    private Integer fileSize;	
    
    /** file ext. */
    @Column(name="fileExt", type="VARCHAR", length="20", nullable="true", comment="文件扩展名")
    private String fileExt;	
    
    /** storage id. */
    @Column(name="storageId", type="VARCHAR", length="100", nullable="false", comment="文件存储id，通过这个id取获取")
    private String storageId;	
    
    /** oraginal name. */
    @Column(name="oraginalName", type="VARCHAR", length="300", nullable="false", comment="文件原名")
    private String oraginalName;	
    
    /** storage driver. */
    @Column(name="storageDriver", type="VARCHAR", length="100", nullable="false", comment="存储驱动名，后台程序记录，可用于问题分析")
    private String storageDriver;	
    
    /** create time. */
    @Column(name="createTime", type="TIMESTAMP", length="19", nullable="false", comment="创建时间")
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