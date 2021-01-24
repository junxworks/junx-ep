/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SOpLog.java   
 * @Package io.github.junxworks.ep.sys.entity   
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
package io.github.junxworks.ep.sys.entity;

import io.github.junxworks.ep.core.orm.annotations.Table;
import io.github.junxworks.ep.core.orm.annotations.PrimaryKey;
import io.github.junxworks.ep.core.orm.annotations.Column;
import java.util.Date;

/**
 * {类的详细说明}.
 *
 * @ClassName:  SOpLog
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
 
@Table(tableName="s_op_log",tableComment="")
public class SOpLog {
	
    /** id. */
    @PrimaryKey
    @Column(name="id", type="BIGINT", length="19", nullable="false", comment="编号")
    private Long id;
    
    /** user id. */
    @Column(name="userId", type="BIGINT", length="19", nullable="true", comment="用户编号")
    private Long userId;	
    
    /** operation. */
    @Column(name="operation", type="VARCHAR", length="255", nullable="true", comment="操作名称")
    private String operation;	
    
    /** url. */
    @Column(name="url", type="LONGVARCHAR", length="65,535", nullable="true", comment="日志url")
    private String url;	
    
    /** ip. */
    @Column(name="ip", type="VARCHAR", length="255", nullable="true", comment="日志ip")
    private String ip;	
    
    /** data. */
    @Column(name="data", type="LONGVARCHAR", length="65,535", nullable="true", comment="请求数据")
    private String data;	
    
    /** method. */
    @Column(name="method", type="LONGVARCHAR", length="65,535", nullable="true", comment="请求方法")
    private String method;	
    
    /** cost. */
    @Column(name="cost", type="BIGINT", length="19", nullable="true", comment="执行耗时")
    private Long cost;	
    
    /** create date. */
    @Column(name="createDate", type="TIMESTAMP", length="19", nullable="true", comment="创建日期")
    private Date createDate;	
    

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
    public String getOperation(){
        return this.operation;
    }

    public void setOperation(String operation){
        this.operation = operation;
    }
    public String getUrl(){
        return this.url;
    }

    public void setUrl(String url){
        this.url = url;
    }
    public String getIp(){
        return this.ip;
    }

    public void setIp(String ip){
        this.ip = ip;
    }
    public String getData(){
        return this.data;
    }

    public void setData(String data){
        this.data = data;
    }
    public String getMethod(){
        return this.method;
    }

    public void setMethod(String method){
        this.method = method;
    }
    public Long getCost(){
        return this.cost;
    }

    public void setCost(Long cost){
        this.cost = cost;
    }
    public Date getCreateDate(){
        return this.createDate;
    }

    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }
}