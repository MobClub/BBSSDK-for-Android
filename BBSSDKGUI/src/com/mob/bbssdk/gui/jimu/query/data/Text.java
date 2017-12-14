package com.mob.bbssdk.gui.jimu.query.data;

/** 文本类型 */
public class Text extends DataType<String> {

	public Text(String value) {
		super(value);
	}

	public static Text valueOf(String value) {
		return new Text(value);
	}

	public static Text[] valueOf(String... values) {
		Text[] ret = new Text[values.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new Text(values[i]);
		}
		return ret;
	}

}
