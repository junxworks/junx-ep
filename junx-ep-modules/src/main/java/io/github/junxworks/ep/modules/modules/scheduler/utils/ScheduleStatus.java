package io.github.junxworks.ep.modules.modules.scheduler.utils;

public enum ScheduleStatus {
	/**
	 * 正常
	 */
	NORMAL(0),
	/**
	 * 暂停
	 */
	PAUSE(1);

	private int value;

	ScheduleStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
