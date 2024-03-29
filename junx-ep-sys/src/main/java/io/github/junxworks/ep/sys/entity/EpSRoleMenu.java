package io.github.junxworks.ep.sys.entity;

import io.github.junxworks.ep.core.orm.annotations.Table;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Column;

/**
 * <p>Entity Class</p>
 * <p>Table: ep_s_role_menu</p>
 *
 * @since 2021-2-19 15:18:04 Generated by JunxPlugin
 */
 
@Table(tableName="ep_s_role_menu",tableComment="")
public class EpSRoleMenu {
	
    @PrimaryKey
    @Column(name="id", type="BIGINT", length="19", nullable="false", comment="编号")
    private Long id;
    
    @Column(name="role_id", type="BIGINT", length="19", nullable="true", comment="角色编号")
    private Long roleId;	
    
    @Column(name="menu_id", type="BIGINT", length="19", nullable="true", comment="菜单编号")
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