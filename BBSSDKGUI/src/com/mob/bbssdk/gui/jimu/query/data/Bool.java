package com.mob.bbssdk.gui.jimu.query.data;

/** 布尔值类型 */
public class Bool extends DataType<Boolean> {

	public Bool(boolean value) {
		super(value);
	}

	public static Bool valueOf(boolean value) {
		return new Bool(value);
	}

	public static Bool[] valueOf(boolean... values) {
		Bool[] ret = new Bool[values.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new Bool(values[i]);
		}
		return ret;
	}
}
