package io.github.junxworks.ep.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.PageUtils;
import io.github.junxworks.ep.sys.annotations.EpLog;
import io.github.junxworks.ep.sys.dto.TopItemConditionDto;
import io.github.junxworks.ep.sys.dto.TopItemDto;
import io.github.junxworks.ep.sys.service.TopItemService;
import io.github.junxworks.ep.sys.vo.TopItemVo;

/**
 * 顶部组件控制器
 */
@RestController
@RequestMapping("/ep/sys/top-items")
public class TopItemController {

	@Autowired
	private TopItemService topItemService;

	/**
	 * 返回 user list 属性.
	 *
	 * @param pageable the pageable
	 * @return user list 属性
	 */
	@GetMapping()
	public Result getTopItemList(TopItemConditionDto condition) {
		PageUtils.setPage(condition);
		return Result.ok(new PageInfo<TopItemVo>(topItemService.queryItemListByCondition(condition)));
	}

	@GetMapping("/{id}")
	public Result getTopItemById(@PathVariable("id") Long id) {
		return Result.ok(topItemService.queryItemById(id));
	}

	@PostMapping()
	@EpLog("EP-系统支撑-保存顶部组件")
	public Result saveUserInfo(@RequestBody TopItemDto dto) {
		topItemService.saveItem(dto);
		return Result.ok();
	}

	/**
	 * Update user status.
	 *
	 * @param userId the user id
	 * @param userInfoDto the user info dto
	 * @return the result
	 */
	@PutMapping(value = "/status")
	@EpLog("EP-系统支撑-更新顶部组件")
	public Result updateUserStatus(@RequestBody TopItemDto dto) {
		return Result.ok(topItemService.saveItem(dto));
	}
}
