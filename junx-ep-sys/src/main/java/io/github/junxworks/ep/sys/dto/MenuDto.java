/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  MenuDto.java   
 * @Package io.github.junxworks.ep.sys.dto   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:42   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.dto;

import io.github.junxworks.ep.core.orm.annotations.Column;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Table;

/**
 * {类的详细说明}.
 *
 * @ClassName:  MenuDto
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
 
@Table(tableName="s_menu",tableComment="")
public class MenuDto {
	
    /** id. */
    @PrimaryKey
    @Column(name="id", type="BIGINT", length="19", nullable="false", comment="菜单编号")
    private Long id;
    
    /** parent id. */
    @Column(name="parentId", type="BIGINT", length="19", nullable="true", comment="上级菜单编号")
    private Long parentId;	
    
    /** name. */
    @Column(name="name", type="VARCHAR", length="255", nullable="true", comment="菜单名称")
    private String name;	
    
    /** type. */
    @Column(name="type", type="TINYINT", length="3", nullable="true", comment="类型 0菜单 1按钮 2目录 ")
    private Byte type;	
    
    /** mark. */
    @Column(name="mark", type="LONGVARCHAR", length="65,535", nullable="true", comment="菜单标记")
    private String mark;	
    
    /** url. */
    @Column(name="url", type="LONGVARCHAR", length="65,535", nullable="true", comment="菜单链接")
    private String url;	
    
    /** icon. */
    @Column(name="icon", type="VARCHAR", length="255", nullable="true", comment="菜单图标")
    private String icon;	
    
    /** sort. */
    @Column(name="sort", type="TINYINT", length="3", nullable="true", comment="排序")
    private Byte sort;	
    
    /** status. */
    @Column(name="status", type="TINYINT", length="3", nullable="true", comment="状态 -1已删除 0正常")
    private Byte status;	
    
    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getParentId(){
        return this.parentId;
    }

    public void setParentId(Long parentId){
        this.parentId = parentId;
    }
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
    public Byte getType(){
        return this.type;
    }

    public void setType(Byte type){
        this.type = type;
    }
    public String getMark(){
        return this.mark;
    }

    public void setMark(String mark){
        this.mark = mark;
    }
    public String getUrl(){
        return this.url;
    }

    public void setUrl(String url){
        this.url = url;
    }
    public String getIcon(){
        return this.icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }
    public Byte getSort(){
        return this.sort;
    }

    public void setSort(Byte sort){
        this.sort = sort;
    }
    public Byte getStatus(){
        return this.status;
    }

    public void setStatus(Byte status){
        this.status = status;
    }
}