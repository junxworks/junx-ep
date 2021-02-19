package io.github.junxworks.ep.sys.entity;

import io.github.junxworks.ep.core.orm.annotations.Table;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Column;
import java.util.Date;

/**
 * <p>Entity Class</p>
 * <p>Table: s_dict</p>
 *
 * @since 2021-2-19 15:18:04 Generated by JunxPlugin
 */
 
@Table(tableName="s_dict",tableComment="")
public class SDict {
	
    @PrimaryKey
    @Column(name="id", type="BIGINT", length="19", nullable="false", comment="编号")
    private Long id;
    
    @Column(name="parent_code", type="VARCHAR", length="100", nullable="true", comment="父编码")
    private String parentCode;	
    
    @Column(name="data_code", type="VARCHAR", length="100", nullable="true", comment="数据项编码")
    private String dataCode;	
    
    @Column(name="data_label", type="VARCHAR", length="200", nullable="true", comment="数据项名称")
    private String dataLabel;	
    
    @Column(name="sort", type="INTEGER", length="10", nullable="true", comment="排序")
    private Integer sort;	
    
    @Column(name="remark", type="VARCHAR", length="200", nullable="true", comment="备注")
    private String remark;	
    
    @Column(name="status", type="TINYINT", length="3", nullable="true", comment="状态 -1已删除 0正常")
    private Byte status;	
    
    @Column(name="create_user", type="BIGINT", length="19", nullable="true", comment="创建人编号")
    private Long createUser;	
    
    @Column(name="create_time", type="TIMESTAMP", length="19", nullable="true", comment="创建日期")
    private Date createTime;	
    
    @Column(name="update_user", type="BIGINT", length="19", nullable="true", comment="修改人编号")
    private Long updateUser;	
    
    @Column(name="update_time", type="TIMESTAMP", length="19", nullable="true", comment="修改日期")
    private Date updateTime;	
    

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
    public String getDataLabel(){
        return this.dataLabel;
    }

    public void setDataLabel(String dataLabel){
        this.dataLabel = dataLabel;
    }
    public Integer getSort(){
        return this.sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }
    public String getRemark(){
        return this.remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }
    public Byte getStatus(){
        return this.status;
    }

    public void setStatus(Byte status){
        this.status = status;
    }
    public Long getCreateUser(){
        return this.createUser;
    }

    public void setCreateUser(Long createUser){
        this.createUser = createUser;
    }
    public Date getCreateTime(){
        return this.createTime;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    public Long getUpdateUser(){
        return this.updateUser;
    }

    public void setUpdateUser(Long updateUser){
        this.updateUser = updateUser;
    }
    public Date getUpdateTime(){
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
}