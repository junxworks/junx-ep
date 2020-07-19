/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SystemLogServiceImpl.java   
 * @Package io.github.junxworks.ep.sys.service.impl   
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
package io.github.junxworks.ep.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import io.github.junxworks.ep.sys.dto.SystemLogConditionDto;
import io.github.junxworks.ep.sys.entity.SOpLog;
import io.github.junxworks.ep.sys.mapper.OpLogMapper;
import io.github.junxworks.ep.sys.service.SystemLogService;
import io.github.junxworks.ep.sys.vo.SystemLogInfoVo;
import io.github.junxworks.ep.core.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {类的详细说明}.
 *
 * @ClassName:  SystemLogServiceImpl
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {

    /** op log mapper. */
    @Autowired
    private OpLogMapper opLogMapper;

    /**
     * 返回 system log list by page 属性.
     *
     * @param condition the condition
     * @return system log list by page 属性
     */
    public PageInfo<SystemLogInfoVo> getSystemLogListByPage(SystemLogConditionDto condition) {
        PageUtils.setPage(condition);
        Page<SystemLogInfoVo> logList = opLogMapper.selectAll(condition);
        PageInfo<SystemLogInfoVo> voPageInfo = new PageInfo<SystemLogInfoVo>(logList);
        return voPageInfo;
    }
    
    /**
     * 返回 system log info by id 属性.
     *
     * @param id the id
     * @return system log info by id 属性
     */
    public SystemLogInfoVo getSystemLogInfoById(Long id) {
        SystemLogInfoVo log = opLogMapper.selectById(id);
        return log;
    }
}
