package io.github.junxworks.ep.modules.modules.sys.vo;

import java.util.Date;
import java.util.List;

/**
 * @Description: 数据字典信息
 * @Author: FengYun
 * @Date: 2019/7/2 16:46
 */
public class DictionaryInfoVo {
    private Long id;

    private String parentCode;

    private String dataCode;

    private String dataValue;

    private Integer sort;

    private String memo;

    private Byte status;

    private Long creatorId;

    private Date createDate;

    private Long modifierId;

    private Date modifyDate;

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
