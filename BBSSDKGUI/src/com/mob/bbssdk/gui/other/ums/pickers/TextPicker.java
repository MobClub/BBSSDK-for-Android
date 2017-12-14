package com.mob.bbssdk.gui.other.ums.pickers;

import com.mob.bbssdk.gui.jimu.gui.Page;
import com.mob.bbssdk.gui.jimu.gui.Theme;

public class TextPicker extends Page<TextPicker> {
	private String titleResName;
	private String hintResName;
	private String defaultValue;
	private boolean numeral;

	public TextPicker(Theme theme) {
		super(theme);
	}

	public void setTitleResName(String resName) {
		this.titleResName = resName;
	}

	public String getTitleResName() {
		return titleResName;
	}

	public void setHintResName(String resName) {
		this.hintResName = resName;
	}

	public String getHintResName() {
		return hintResName;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setNumeral() {
		numeral = true;
	}

	public boolean isNumeral() {
		return numeral;
	}

}
