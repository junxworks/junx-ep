package io.github.junxworks.ep.sys.entity;

import io.github.junxworks.ep.core.orm.annotations.Table;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Column;

/**
 * <p>Entity Class</p>
 * <p>Table: s_profile</p>
 *
 * @since 2021-2-19 15:18:04 Generated by JunxPlugin
 */
 
@Table(tableName="s_profile",tableComment="")
public class SProfile {
	
    @PrimaryKey
    @Column(name="id", type="BIGINT", length="19", nullable="false", comment="编号")
    private Long id;
    
    @Column(name="name", type="VARCHAR", length="100", nullable="true", comment="配置项名称")
    private String name;	
    
    @Column(name="value", type="VARCHAR", length="200", nullable="true", comment="配置值")
    private String value;	
    
    @Column(name="remark", type="VARCHAR", length="100", nullable="true", comment="备注")
    private String remark;	
    

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
    public String getValue(){
        return this.value;
    }

    public void setValue(String value){
        this.value = value;
    }
    public String getRemark(){
        return this.remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }
}