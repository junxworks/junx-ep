package io.github.junxworks.ep.modules.modules.sys.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.modules.annotations.OpLog;
import io.github.junxworks.ep.modules.constants.RecordStatus;
import io.github.junxworks.ep.modules.modules.sys.dto.DictionaryPageable;
import io.github.junxworks.ep.modules.modules.sys.service.DictionaryService;
import io.github.junxworks.ep.modules.modules.sys.vo.DictionaryInfoVo;
import io.github.junxworks.ep.auth.model.UserModel;


/**
 * @Description: 数据字典
 * @Author: FengYun
 * @Date: 2019/7/1 10:34
 */
@RestController
@RequestMapping("/ep/sys")
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 数据字典列表
     */
    @GetMapping(value="/dictionaries")
    public Result getdictionaryList(DictionaryPageable pageable){
        Result result = Result.ok();
        pageable.setStatus(RecordStatus.NORMAL.getValue());
        PageInfo<DictionaryInfoVo> dictionaryPage = dictionaryService.getDictionaryListByPage(pageable);
        result.setData(dictionaryPage);
        return result;
    }

    /**
     * 保存数据字典
     */
    @PostMapping(value = "/dictionaries", consumes = "application/json")
    @OpLog("保存数据字典信息")
    public Result saveDictionaryInfo(@RequestBody DictionaryInfoVo dictionaryInfo){
        Result result = Result.ok();
        try {
            DictionaryPageable dto = new DictionaryPageable();
            dto.setParentCode(dictionaryInfo.getParentCode());
            dto.setDataCode(dictionaryInfo.getDataCode());
            dto.setStatus(RecordStatus.NORMAL.getValue());
            DictionaryInfoVo info = dictionaryService.getDicByCode(dto);
            if(info!=null&&!info.getId().equals(dictionaryInfo.getId())){
                return Result.warn("已存在该编码数据字典");
            }
            Subject sub = SecurityUtils.getSubject();
            UserModel user =(UserModel)sub.getPrincipal();
            dictionaryInfo.setCreatorId(user.getId());
            dictionaryInfo.setCreateDate(new Date());
            if(dictionaryInfo.getId()==null) {
                dictionaryInfo.setStatus(RecordStatus.NORMAL.getValue());
                dictionaryService.postDictionaryInfo(dictionaryInfo);
            }else {
                DictionaryInfoVo newdata = dictionaryService.getDictionaryInfoById(dictionaryInfo.getId());
                newdata.setParentCode(dictionaryInfo.getParentCode());
                newdata.setDataCode(dictionaryInfo.getDataCode());
                newdata.setDataValue(dictionaryInfo.getDataValue());
                newdata.setMemo(dictionaryInfo.getMemo());
                newdata.setSort(dictionaryInfo.getSort());
                newdata.setModifierId(user.getId());
                newdata.setModifyDate(new Date());
                dictionaryService.putDictionaryInfo(newdata);
            }
        }catch(Exception e)
        {
            return Result.error(e.getMessage());
        }
        return result;
    }
    @GetMapping("/dictionaries/{id}")
    public Result getDictionaryInfoById(@PathVariable Long id)
    {
        Result result = new Result();
        DictionaryInfoVo condition = new DictionaryInfoVo();
        try {
            condition=dictionaryService.getDictionaryInfoById(id);
            result.setData(condition);
        } catch (Exception e) {
            //log.error("查询还款方式配置异常", e);
            result.setMsg("查询异常");
            result.setCode(Result.Status.ERROR.getCode());
        }
        return result;
    }
    @DeleteMapping("/dictionaries/{id}")
    @OpLog("删除数据字典信息")
    public Result putDictionaryInfo(@PathVariable("id") Long id) {
        try {
            DictionaryInfoVo dictionaryInfo = new DictionaryInfoVo();
            dictionaryInfo.setId(id);
            dictionaryInfo.setStatus(RecordStatus.DELETED.getValue());
            dictionaryService.deleteDictionaryInfo(dictionaryInfo);
        }catch(Exception e)
        {
            return Result.error(e.getMessage());
        }
        return Result.ok();
    }

    @GetMapping(value="/parentCode/dictionaries")
    public Result getDictionaryGroupParentCode(DictionaryPageable pageable){
        Result result = Result.ok();
        pageable.setPageNo(0);
        pageable.setPageSize(Integer.MAX_VALUE);
        pageable.setStatus(RecordStatus.NORMAL.getValue());
        List<DictionaryInfoVo> dictionary = dictionaryService.getParentCode(pageable);
        for (DictionaryInfoVo dicVo : dictionary){
            pageable.setParentCode(dicVo.getParentCode());
            List<DictionaryInfoVo> childList = dictionaryService.getDictionaryListByPage(pageable).getList();
            dicVo.setList(childList);
        }
        result.setData(dictionary);
        return result;
    }
}
