package io.github.junxworks.ep.modules.modules.sys.vo;

import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @Description: 日志信息
 * @Author: FengYun
 * @Date: 2019/7/2 10:46
 */
public class SystemLogInfoVo {
    private Long id;

    private Long userId;

    private String operation;

    private String url;

    private String ip;

    private String data;

    private String method;

    private Long cost;

    private Date createDate;

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
