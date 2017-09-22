package com.mob.bbssdk.gui.datadef;


public enum ThreadListSelectType {
	//		 * @param selectType    displayOrder 置顶，digest 精华，heats 热门，latest 最新；默认按最新
	LATEST("latest"),
	HEATS("heats"),
	DIGEST("digest"),
	DISPLAY_ORDER("displayOrder");

	private String value;

	ThreadListSelectType(String str) {
		this.value = str;
	}

	public String getValue() {
		return value;
	}
}
