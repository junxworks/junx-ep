package io.github.junxworks.ep.sys.dto;

import io.github.junxworks.ep.core.Pageable;

/**
 * <p>Entity Class</p>
 * <p>Table: ep_s_top_item</p>
 *
 * @since 2021年9月9日 下午2:57:04 Generated by JunxPlugin
 */

public class TopItemConditionDto extends Pageable {

	private String itemName;

	private Byte status;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

}