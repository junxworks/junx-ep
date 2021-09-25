/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  SystemLogServiceImpl.java   
 * @Package io.github.junxworks.ep.sys.service.impl   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-24 15:35:41   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.junxworks.ep.sys.dto.SystemLogConditionDto;
import io.github.junxworks.ep.sys.entity.EpSLog;
import io.github.junxworks.ep.sys.mapper.OpLogMapper;
import io.github.junxworks.ep.sys.service.SysLogService;
import io.github.junxworks.ep.sys.vo.SLogVo;

/**
 * {类的详细说明}.
 *
 * @ClassName:  SystemLogServiceImpl
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@Service("JunxEPSystemLogService")
public class SysLogServiceImpl implements SysLogService {

    /** op log mapper. */
    @Autowired
    private OpLogMapper opLogMapper;

    /**
     * 返回 system log list by page 属性.
     *
     * @param condition the condition
     * @return system log list by page 属性
     */
    public List<SLogVo> getSystemLogListByCondition(SystemLogConditionDto condition) {
        return opLogMapper.selectAll(condition);
    }
    
    /**
     * 返回 system log info by id 属性.
     *
     * @param id the id
     * @return system log info by id 属性
     */
    public SLogVo getSystemLogInfoById(Long id) {
        SLogVo log = opLogMapper.selectById(id);
        return log;
    }

	@Override
	public int saveSystemLog(EpSLog log) {
		return opLogMapper.insertWithoutNull(log);
	}
}
