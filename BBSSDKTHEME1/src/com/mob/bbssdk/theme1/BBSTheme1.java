package com.mob.bbssdk.theme1;


import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;

public class BBSTheme1 {
	protected static BBSTheme1ViewBuilder bbsTheme1ViewBuilder;
	protected static Theme1ListViewItemBuilder theme1ListViewItemBuilder;

	public static void init() {
		if (bbsTheme1ViewBuilder == null) {
			bbsTheme1ViewBuilder = new BBSTheme1ViewBuilder();
		}
		if (theme1ListViewItemBuilder == null) {
			theme1ListViewItemBuilder = new Theme1ListViewItemBuilder();
		}
		BBSViewBuilder.init(bbsTheme1ViewBuilder);
		ListViewItemBuilder.init(theme1ListViewItemBuilder);
	}
}
