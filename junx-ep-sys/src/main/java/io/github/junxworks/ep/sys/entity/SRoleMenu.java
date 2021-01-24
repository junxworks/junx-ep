/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SRoleMenu.java   
 * @Package io.github.junxworks.ep.sys.entity   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:41   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
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
 * @ClassName:  SRoleMenu
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
 
@Table(tableName="s_role_menu",tableComment="")
public class SRoleMenu {
	
    /** id. */
    @PrimaryKey
    @Column(name="id", type="BIGINT", length="19", nullable="false", comment="编号")
    private Long id;
    
    /** role id. */
    @Column(name="roleId", type="BIGINT", length="19", nullable="true", comment="角色编号")
    private Long roleId;	
    
    /** menu id. */
    @Column(name="menuId", type="BIGINT", length="19", nullable="true", comment="菜单编号")
    private Long menuId;	
    

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getRoleId(){
        return this.roleId;
    }

    public void setRoleId(Long roleId){
        this.roleId = roleId;
    }
    public Long getMenuId(){
        return this.menuId;
    }

    public void setMenuId(Long menuId){
        this.menuId = menuId;
    }
}