/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SUserRole.java   
 * @Package io.github.junxworks.ep.sys.entity   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:17:47   
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

/**
 * {类的详细说明}.
 *
 * @ClassName:  SUserRole
 * @author: Michael
 * @date:   2020-7-19 12:17:47
 * @since:  v1.0
 */
 
@Table(tableName="s_user_role",tableComment="")
public class SUserRole {
	
    /** id. */
    @PrimaryKey
    @Column(name="id", type="BIGINT", length="19", nullable="false", comment="编号")
    private Long id;
    
    /** user id. */
    @Column(name="userId", type="BIGINT", length="19", nullable="false", comment="用户编号")
    private Long userId;	
    
    /** role id. */
    @Column(name="roleId", type="BIGINT", length="19", nullable="false", comment="角色编号")
    private Long roleId;	
    

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getUserId(){
        return this.userId;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }
    public Long getRoleId(){
        return this.roleId;
    }

    public void setRoleId(Long roleId){
        this.roleId = roleId;
    }
}