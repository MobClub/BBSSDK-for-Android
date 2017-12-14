package com.mob.bbssdk.gui.jimu.query.data;

public abstract class DataType<T> {
	protected T value;

	public DataType(T value) {
		this.value = value;
	}

	public Object value() {
		return value;
	}
}
