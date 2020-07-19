/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  DictionaryInfoVo.java   
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

import java.util.Date;
import java.util.List;

/**
 * {类的详细说明}.
 *
 * @ClassName:  DictionaryInfoVo
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
public class DictionaryInfoVo {
    
    /** id. */
    private Long id;

    /** parent code. */
    private String parentCode;

    /** data code. */
    private String dataCode;

    /** data value. */
    private String dataValue;

    /** sort. */
    private Integer sort;

    /** memo. */
    private String memo;

    /** status. */
    private Byte status;

    /** creator id. */
    private Long creatorId;

    /** create date. */
    private Date createDate;

    /** modifier id. */
    private Long modifierId;

    /** modify date. */
    private Date modifyDate;

    /** list. */
    private List<DictionaryInfoVo> list;


    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getParentCode(){
        return this.parentCode;
    }

    public void setParentCode(String parentCode){
        this.parentCode = parentCode;
    }
    public String getDataCode(){
        return this.dataCode;
    }

    public void setDataCode(String dataCode){
        this.dataCode = dataCode;
    }
    public String getDataValue(){
        return this.dataValue;
    }

    public void setDataValue(String dataValue){
        this.dataValue = dataValue;
    }
    public Integer getSort(){
        return this.sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }
    public String getMemo(){
        return this.memo;
    }

    public void setMemo(String memo){
        this.memo = memo;
    }
    public Byte getStatus(){
        return this.status;
    }

    public void setStatus(Byte status){
        this.status = status;
    }
    public Long getCreatorId(){
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId){
        this.creatorId = creatorId;
    }
    public Date getCreateDate(){
        return this.createDate;
    }

    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }
    public Long getModifierId(){
        return this.modifierId;
    }

    public void setModifierId(Long modifierId){
        this.modifierId = modifierId;
    }
    public Date getModifyDate(){
        return this.modifyDate;
    }

    public void setModifyDate(Date modifyDate){
        this.modifyDate = modifyDate;
    }

    public List<DictionaryInfoVo> getList() {
        return list;
    }

    public void setList(List<DictionaryInfoVo> list) {
        this.list = list;
    }
}
