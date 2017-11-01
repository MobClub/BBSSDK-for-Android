package com.mob.bbssdk.theme0;


import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;

public class BBSTheme0 {
	protected static BBSTheme0ViewBuilder bbsTheme0ViewBuilder;
	protected static Theme0ListViewItemBuilder theme0ListViewItemBuilder;

	public static void init() {
		if (bbsTheme0ViewBuilder == null) {
			bbsTheme0ViewBuilder = new BBSTheme0ViewBuilder();
		}
		if (theme0ListViewItemBuilder == null) {
			theme0ListViewItemBuilder = new Theme0ListViewItemBuilder();
		}
		BBSViewBuilder.init(BBSTheme0ViewBuilder.buildInstance());
		ListViewItemBuilder.init(new Theme0ListViewItemBuilder());
	}
}
