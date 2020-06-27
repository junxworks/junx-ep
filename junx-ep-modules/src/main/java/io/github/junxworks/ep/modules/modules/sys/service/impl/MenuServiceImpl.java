package io.github.junxworks.ep.modules.modules.sys.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.github.junxworks.ep.modules.modules.sys.dto.MenuPageable;
import io.github.junxworks.ep.modules.modules.sys.entity.SMenu;
import io.github.junxworks.ep.modules.modules.sys.mapper.MenuMapper;
import io.github.junxworks.ep.modules.modules.sys.service.MenuService;
import io.github.junxworks.ep.modules.modules.sys.vo.MenuInfoVo;
import io.github.junxworks.ep.modules.modules.sys.vo.RoleInfoVo;
import io.github.junxworks.ep.core.utils.PageUtils;

/**
 * @Description: 菜单信息
 * @Author: FengYun
 * @Date: 2019/7/1 10:40
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 分页查询菜单信息
     */
    @Override
    public List<MenuInfoVo> getAllMenuItems() {
        return menuMapper.selectAllMenusItems();
    }
    
    /**
     * 分页查询菜单信息
     */
    @Override
    public PageInfo<MenuInfoVo> getMenuListByPage(MenuPageable pageable) {
        PageUtils.setPage(pageable);
        Page<MenuInfoVo> menuList = menuMapper.selectAll(pageable);
        PageInfo<MenuInfoVo> voPageInfo = new PageInfo<MenuInfoVo>(menuList);
        return voPageInfo;
    }
    /**
     * 分页查询菜单信息
     */
    @Override
    public List<MenuInfoVo> getAllMenuList() {
        return menuMapper.selectAllMenus();
    }
    
    @Override
    public List<MenuInfoVo> getAllMenuByUserId(long userId) {
        return menuMapper.getAllMenuByUserId(userId);
    }
    /**
     * 根据角色ID获取菜单列表
     */
    @Override
    public List<MenuInfoVo> getMenuListByRoleId(Long roleId) {
        return menuMapper.selectByRoleId(roleId);
    }

    /**
     * 通过id查询菜单信息
     */
    @Override
    public MenuInfoVo getMenuInfoById(Long menuId) {
        return menuMapper.selectById(menuId);
    }

    /**
     * 新增菜单信息
     */
    @Override
    public int postMenuInfo(MenuInfoVo menuInfoVo) {
        SMenu menu = new SMenu();
        BeanUtils.copyProperties(menuInfoVo, menu);
        return menuMapper.insertWithoutNull(menu);
    }

    /**
     * 修改菜单信息
     */
    @Override
    public int putMenuInfo(MenuInfoVo menuInfoVo) {
        SMenu menu = new SMenu();
        BeanUtils.copyProperties(menuInfoVo, menu);
        return menuMapper.updateWithNull(menu);
    }



    /**
     * 删除菜单信息
     */
    public int deleteMenuInfo(MenuInfoVo menuInfoVo){
        SMenu menu = new SMenu();
        BeanUtils.copyProperties(menuInfoVo,menu);
        return menuMapper.updateWithoutNull(menu);
    }
    @Override
    public int selectChildrenCountById(Long id){
        return menuMapper.selectChildrenCountById(id);
    }

	@Override
	public List<RoleInfoVo> queryRolesByMenuId(Long menuId) {
		return menuMapper.queryRolesByMenuId(menuId);
	}
}
