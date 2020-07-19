/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SSqls.java   
 * @Package io.github.junxworks.ep.sys.entity   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:17:48   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.entity;

import io.github.junxworks.ep.core.orm.annotations.Table;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Column;
import java.util.Date;

/**
 * {类的详细说明}.
 *
 * @ClassName:  SSqls
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
 
@Table(tableName="s_sqls",tableComment="")
public class SSqls {
	
    /** id. */
    @PrimaryKey
    @Column(name="id", type="BIGINT", length="19", nullable="false", comment="编号")
    private Long id;
    
    /** creator id. */
    @Column(name="creatorId", type="BIGINT", length="19", nullable="true", comment="创建人编号")
    private Long creatorId;	
    
    /** create date. */
    @Column(name="createDate", type="TIMESTAMP", length="19", nullable="true", comment="创建日期")
    private Date createDate;	
    
    /** modifier id. */
    @Column(name="modifierId", type="BIGINT", length="19", nullable="true", comment="修改人编号")
    private Long modifierId;	
    
    /** modify date. */
    @Column(name="modifyDate", type="TIMESTAMP", length="19", nullable="true", comment="修改日期")
    private Date modifyDate;	
    
    /** status. */
    @Column(name="status", type="TINYINT", length="3", nullable="true", comment="状态 -1已删除 0正常")
    private Byte status;	
    
    /** uid. */
    @Column(name="uid", type="VARCHAR", length="200", nullable="false", comment="sql的唯一ID")
    private String uid;	
    
    /** sql content. */
    @Column(name="sqlContent", type="VARCHAR", length="4,000", nullable="true", comment="sql内容")
    private String sqlContent;	
    
    /** description. */
    @Column(name="description", type="VARCHAR", length="200", nullable="true", comment="描述")
    private String description;	
    
    /** category. */
    @Column(name="category", type="VARCHAR", length="200", nullable="true", comment="所属分类")
    private String category;	
    

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getCreatorId(){
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId){
        this.creatorId = creatorId;
    }
    public Date getCreateDate(){
        return this.createDate;
    }

    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }
    public Long getModifierId(){
        return this.modifierId;
    }

    public void setModifierId(Long modifierId){
        this.modifierId = modifierId;
    }
    public Date getModifyDate(){
        return this.modifyDate;
    }

    public void setModifyDate(Date modifyDate){
        this.modifyDate = modifyDate;
    }
    public Byte getStatus(){
        return this.status;
    }

    public void setStatus(Byte status){
        this.status = status;
    }
    public String getUid(){
        return this.uid;
    }

    public void setUid(String uid){
        this.uid = uid;
    }
    public String getSqlContent(){
        return this.sqlContent;
    }

    public void setSqlContent(String sqlContent){
        this.sqlContent = sqlContent;
    }
    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getCategory(){
        return this.category;
    }

    public void setCategory(String category){
        this.category = category;
    }
}