package io.github.junxworks.ep.sys.entity;

import io.github.junxworks.ep.core.orm.annotations.Table;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Column;
import java.util.Date;

/**
 * <p>Entity Class</p>
 * <p>Table: ep_s_user</p>
 *
 * @since 2021-2-19 15:18:04 Generated by JunxPlugin
 */
 
@Table(tableName="ep_s_user",tableComment="")
public class EpSUser {
	
    @PrimaryKey
    @Column(name="id", type="BIGINT", length="19", nullable="false", comment="用户编号")
    private Long id;
    
    @Column(name="name", type="VARCHAR", length="50", nullable="false", comment="用户姓名")
    private String name;	
    
    @Column(name="username", type="VARCHAR", length="50", nullable="true", comment="账号")
    private String username;	
    
    @Column(name="password", type="VARCHAR", length="100", nullable="true", comment="登录密码")
    private String password;	
    
    @Column(name="mobile", type="VARCHAR", length="11", nullable="true", comment="手机号码")
    private String mobile;	
    
    @Column(name="id_card", type="VARCHAR", length="20", nullable="true", comment="身份证号码")
    private String idCard;	
    
    @Column(name="email", type="VARCHAR", length="30", nullable="true", comment="邮箱")
    private String email;	
    
    @Column(name="org_no", type="VARCHAR", length="50", nullable="true", comment="组织编码")
    private String orgNo;	
    
    @Column(name="create_user", type="BIGINT", length="19", nullable="true", comment="创建人编号")
    private Long createUser;	
    
    @Column(name="create_time", type="TIMESTAMP", length="19", nullable="true", comment="创建日期")
    private Date createTime;	
    
    @Column(name="update_user", type="BIGINT", length="19", nullable="true", comment="修改人编号")
    private Long updateUser;	
    
    @Column(name="update_time", type="TIMESTAMP", length="19", nullable="true", comment="修改日期")
    private Date updateTime;	
    
    @Column(name="status", type="TINYINT", length="3", nullable="true", comment="状态 0：正常，1：冻结，2：锁定 ")
    private Byte status;	
    

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getMobile(){
        return this.mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public String getIdCard(){
        return this.idCard;
    }

    public void setIdCard(String idCard){
        this.idCard = idCard;
    }
    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getOrgNo(){
        return this.orgNo;
    }

    public void setOrgNo(String orgNo){
        this.orgNo = orgNo;
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
}