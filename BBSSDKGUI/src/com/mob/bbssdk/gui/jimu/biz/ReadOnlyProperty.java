package com.mob.bbssdk.gui.jimu.biz;

public class ReadOnlyProperty<T> {
	private final String name;
	private final Class<T> type;
	private T value;

	public ReadOnlyProperty(String name, Class<T> type) {
		this.name = name;
		this.type = type;
	}

	public final String getName() {
		return name;
	}

	public final Class<T> getType() {
		return type;
	}

	public String toString() {
		return name + " (" + type.getName() + ") = " + value;
	}

	protected void onSet(T value) {
		this.value = value;
	}

	public boolean isNull() {
		return value == null;
	}

	@SuppressWarnings("unchecked")
	protected T onGet() {
		if (Byte.class.equals(type)) {
			return value == null ? (T) Byte.valueOf((byte) 0) : value;
		} else if (Short.class.equals(type)) {
			return value == null ? (T) Short.valueOf((short) 0) : value;
		} else if (Integer.class.equals(type)) {
			return value == null ? (T) Integer.valueOf(0) : value;
		} else if (Long.class.equals(type)) {
			return value == null ? (T) Long.valueOf(0) : value;
		} else if (Float.class.equals(type)) {
			return value == null ? (T) Float.valueOf(0) : value;
		} else if (Double.class.equals(type)) {
			return value == null ? (T) Double.valueOf(0) : value;
		} else if (Character.class.equals(type)) {
			return value == null ? (T) Character.valueOf((char) 0) : value;
		} else if (Boolean.class.equals(type)) {
			return value == null ? (T) Boolean.valueOf(false) : value;
		}
		return value;
	}

	public final T get() {
		return onGet();
	}

}
