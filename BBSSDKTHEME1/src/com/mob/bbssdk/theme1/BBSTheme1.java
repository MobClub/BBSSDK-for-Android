package com.mob.bbssdk.theme1;


import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.builder.ListViewItemBuilder;

public class BBSTheme1 {
	public static void init() {
		BBSViewBuilder.init(new BBSTheme1ViewBuilder());
		ListViewItemBuilder.init(new Theme1ListViewItemBuilder());
	}
}
