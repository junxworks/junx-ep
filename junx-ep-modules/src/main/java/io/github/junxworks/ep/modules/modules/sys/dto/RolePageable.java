package io.github.junxworks.ep.modules.modules.sys.dto;

import io.github.junxworks.ep.core.Pageable;

/**
 * @Description: 角色列表过滤条件
 * @Author: ChenYang
 * @Date: 2019/7/1 10:02
 */
public class RolePageable extends Pageable {

    /**
     * 搜索关键字
     */
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
