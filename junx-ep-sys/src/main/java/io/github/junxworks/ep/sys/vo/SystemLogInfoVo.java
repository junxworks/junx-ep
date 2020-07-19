/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SystemLogInfoVo.java   
 * @Package io.github.junxworks.ep.sys.vo   
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
package io.github.junxworks.ep.sys.vo;

import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * {类的详细说明}.
 *
 * @ClassName:  SystemLogInfoVo
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
public class SystemLogInfoVo {
    
    /** id. */
    private Long id;

    /** user id. */
    private Long userId;

    /** operation. */
    private String operation;

    /** url. */
    private String url;

    /** ip. */
    private String ip;

    /** data. */
    private String data;

    /** method. */
    private String method;

    /** cost. */
    private Long cost;

    /** create date. */
    private Date createDate;

    /** name. */
    private String name;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
