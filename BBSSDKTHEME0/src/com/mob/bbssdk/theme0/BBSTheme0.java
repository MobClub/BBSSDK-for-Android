package com.mob.bbssdk.theme0;


import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;

public class BBSTheme0 {
	public static void init() {
		BBSViewBuilder.init(BBSTheme0ViewBuilder.buildInstance());
		ListViewItemBuilder.init(new Theme0ListViewItemBuilder());
	}
}
