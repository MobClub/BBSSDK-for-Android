package com.mob.bbssdk.gui.datadef;


public enum ThreadListOrderType {
	//	 * @param orderType        排序依据 ：createdOn 按照发帖时间排序，lastPost 按照回帖时间排序；默认按发帖时间排序
	CREATE_ON("createOn"),
	LAST_POST("lastPost");

	private String value;

	ThreadListOrderType(String str) {
		this.value = str;
	}

	public String getValue() {
		return value;
	}
}
