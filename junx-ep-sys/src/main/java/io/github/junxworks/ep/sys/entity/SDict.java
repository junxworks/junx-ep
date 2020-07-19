/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SDict.java   
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
 * @ClassName:  SDict
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
 
@Table(tableName="s_dict",tableComment="")
public class SDict {
	
    /** id. */
    @PrimaryKey
    @Column(name="id", type="BIGINT", length="19", nullable="false", comment="编号")
    private Long id;
    
    /** parent code. */
    @Column(name="parentCode", type="VARCHAR", length="50", nullable="true", comment="父编码")
    private String parentCode;
    
    /** data code. */
    @Column(name="dataCode", type="VARCHAR", length="50", nullable="true", comment="数据编码")
    private String dataCode;
    
    /** data value. */
    @Column(name="dataValue", type="VARCHAR", length="200", nullable="true", comment="数据值")
    private String dataValue;	
    
    /** sort. */
    @Column(name="sort", type="INTEGER", length="10", nullable="true", comment="排序")
    private Integer sort;	
    
    /** memo. */
    @Column(name="memo", type="VARCHAR", length="200", nullable="true", comment="字段描述")
    private String memo;	
    
    /** status. */
    @Column(name="status", type="TINYINT", length="3", nullable="true", comment="状态 -1已删除 0正常")
    private Byte status;	
    
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
    

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getParentCode(){
        return this.parentCode;
    }

    public void setParentCode(String parentCode){
        this.parentCode = parentCode;
    }
    public String getDataCode(){
        return this.dataCode;
    }

    public void setDataCode(String dataCode){
        this.dataCode = dataCode;
    }
    public String getDataValue(){
        return this.dataValue;
    }

    public void setDataValue(String dataValue){
        this.dataValue = dataValue;
    }
    public Integer getSort(){
        return this.sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }
    public String getMemo(){
        return this.memo;
    }

    public void setMemo(String memo){
        this.memo = memo;
    }
    public Byte getStatus(){
        return this.status;
    }

    public void setStatus(Byte status){
        this.status = status;
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
}