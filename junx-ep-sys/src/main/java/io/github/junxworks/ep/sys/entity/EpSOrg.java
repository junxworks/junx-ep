package io.github.junxworks.ep.sys.entity;

import io.github.junxworks.ep.core.orm.annotations.Table;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Column;
import java.util.Date;

/**
 * <p>Entity Class</p>
 * <p>Table: ep_s_org</p>
 *
 * @since 2021-2-19 15:18:04 Generated by JunxPlugin
 */
 
@Table(tableName="ep_s_org",tableComment="")
public class EpSOrg {
	
    @PrimaryKey
    @Column(name="id", type="BIGINT", length="19", nullable="false", comment="编号")
    private Long id;
    
    @Column(name="create_user", type="BIGINT", length="19", nullable="true", comment="创建人编号")
    private Long createUser;	
    
    @Column(name="create_time", type="TIMESTAMP", length="19", nullable="true", comment="创建日期")
    private Date createTime;	
    
    @Column(name="update_user", type="BIGINT", length="19", nullable="true", comment="修改人编号")
    private Long updateUser;	
    
    @Column(name="update_time", type="TIMESTAMP", length="19", nullable="true", comment="修改日期")
    private Date updateTime;	
    
    @Column(name="status", type="TINYINT", length="3", nullable="true", comment="状态 -1已删除 0正常")
    private Byte status;	
    
    @Column(name="org_no", type="VARCHAR", length="50", nullable="true", comment="组织编码")
    private String orgNo;	
    
    @Column(name="org_name", type="VARCHAR", length="100", nullable="true", comment="组织名称")
    private String orgName;	
    
    @Column(name="org_type", type="VARCHAR", length="20", nullable="true", comment="机构类型 参考码表机构类型 orgType")
    private String orgType;	
    
    @Column(name="parent_no", type="VARCHAR", length="50", nullable="true", comment="直接上级机构编码")
    private String parentNo;	
    
    @Column(name="top_level_no", type="VARCHAR", length="50", nullable="true", comment="顶级机构编码")
    private String topLevelNo;	
    
    @Column(name="org_path", type="VARCHAR", length="200", nullable="true", comment="组织路径")
    private String orgPath;	
    
    @Column(name="remark", type="VARCHAR", length="200", nullable="true", comment="备注")
    private String remark;	
    

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
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
    public Byte getStatus(){
        return this.status;
    }

    public void setStatus(Byte status){
        this.status = status;
    }
    public String getOrgNo(){
        return this.orgNo;
    }

    public void setOrgNo(String orgNo){
        this.orgNo = orgNo;
    }
    public String getOrgName(){
        return this.orgName;
    }

    public void setOrgName(String orgName){
        this.orgName = orgName;
    }
    public String getOrgType(){
        return this.orgType;
    }

    public void setOrgType(String orgType){
        this.orgType = orgType;
    }
    public String getParentNo(){
        return this.parentNo;
    }

    public void setParentNo(String parentNo){
        this.parentNo = parentNo;
    }
    public String getTopLevelNo(){
        return this.topLevelNo;
    }

    public void setTopLevelNo(String topLevelNo){
        this.topLevelNo = topLevelNo;
    }
    public String getOrgPath(){
        return this.orgPath;
    }

    public void setOrgPath(String orgPath){
        this.orgPath = orgPath;
    }
    public String getRemark(){
        return this.remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }
}