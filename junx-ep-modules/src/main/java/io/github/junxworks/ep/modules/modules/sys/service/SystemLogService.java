package io.github.junxworks.ep.modules.modules.sys.service;

import com.github.pagehelper.PageInfo;
import io.github.junxworks.ep.modules.modules.sys.dto.SystemLogConditionDto;
import io.github.junxworks.ep.modules.modules.sys.vo.SystemLogInfoVo;

/**
 * @Description: 日志信息
 * @Author: FengYun
 * @Date: 2019/7/2 10:40
 */

public interface SystemLogService {
    /**
     * 分页查询日志信息
     * @param pageable 分页条件
     * @return 日志列表
     */
    PageInfo<SystemLogInfoVo> getSystemLogListByPage(SystemLogConditionDto condition);
    /**
     * 通过id查询日志信息
     */
    SystemLogInfoVo getSystemLogInfoById(Long menuId);
}
