/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SystemLogService.java   
 * @Package io.github.junxworks.ep.sys.service   
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
package io.github.junxworks.ep.sys.service;

import java.util.List;

import io.github.junxworks.ep.sys.dto.SystemLogConditionDto;
import io.github.junxworks.ep.sys.entity.EpSLog;
import io.github.junxworks.ep.sys.vo.SLogVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  SystemLogService
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */

public interface SysLogService {
    
    /**
     * 返回 system log list by page 属性.
     *
     * @param condition the condition
     * @return system log list by page 属性
     */
    List<SLogVo> getSystemLogListByCondition(SystemLogConditionDto condition);
    
    /**
     * 返回 system log info by id 属性.
     *
     * @param menuId the menu id
     * @return system log info by id 属性
     */
    SLogVo getSystemLogInfoById(Long menuId);
    
    int saveSystemLog(EpSLog log);
}
