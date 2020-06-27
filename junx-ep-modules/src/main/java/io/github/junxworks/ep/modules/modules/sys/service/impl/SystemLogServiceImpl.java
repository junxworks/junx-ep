package io.github.junxworks.ep.modules.modules.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.github.junxworks.ep.modules.modules.sys.dto.SystemLogConditionDto;
import io.github.junxworks.ep.modules.modules.sys.entity.SOpLog;
import io.github.junxworks.ep.modules.modules.sys.mapper.OpLogMapper;
import io.github.junxworks.ep.modules.modules.sys.service.SystemLogService;
import io.github.junxworks.ep.modules.modules.sys.vo.SystemLogInfoVo;
import io.github.junxworks.ep.core.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 日志信息
 * @Author: FengYun
 * @Date: 2019/7/2 10:40
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {

    @Autowired
    private OpLogMapper opLogMapper;

    /**
     * 分页查询菜单信息
     */
    public PageInfo<SystemLogInfoVo> getSystemLogListByPage(SystemLogConditionDto condition) {
        PageUtils.setPage(condition);
        Page<SystemLogInfoVo> logList = opLogMapper.selectAll(condition);
        PageInfo<SystemLogInfoVo> voPageInfo = new PageInfo<SystemLogInfoVo>(logList);
        return voPageInfo;
    }
    /**
     * 通过id查询菜单信息
     */
    public SystemLogInfoVo getSystemLogInfoById(Long id) {
        SystemLogInfoVo log = opLogMapper.selectById(id);
        return log;
    }
}
