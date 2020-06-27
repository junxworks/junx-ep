package io.github.junxworks.ep.modules.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.modules.modules.sys.dto.SystemLogConditionDto;
import io.github.junxworks.ep.modules.modules.sys.service.SystemLogService;
import io.github.junxworks.ep.modules.modules.sys.vo.SystemLogInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @Description: 菜单
 * @Author: FengYun
 * @Date: 2019/7/1 10:34
 */
@RestController
@RequestMapping("/ep/sys")
public class SystemLogController {
    @Autowired
    private SystemLogService systemLogService;

    /**
     * 菜单列表
     */
    @GetMapping(value="/system-logs")
    public Result getSystemLogList(SystemLogConditionDto condition){
        Result result = Result.ok();
        PageInfo<SystemLogInfoVo> menuPage = systemLogService.getSystemLogListByPage(condition);
        result.setData(menuPage);
        return result;
    }
    @GetMapping("/system-logs/{id}")
    public Result getSystemLogInfoById(@PathVariable Long id)
    {
        Result result = new Result();
        SystemLogInfoVo condition = new SystemLogInfoVo();
        try {
            condition=systemLogService.getSystemLogInfoById(id);
            result.setData(condition);
        } catch (Exception e) {
            result.setMsg("查询异常");
            result.setCode(Result.Status.ERROR.getCode());
        }
        return result;
    }
}
