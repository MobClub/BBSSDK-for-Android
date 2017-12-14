package com.mob.bbssdk.gui.jimu.biz;

public class ReadWriteProperty<T> extends ReadOnlyProperty<T> {

	public ReadWriteProperty(String name, Class<T> type) {
		super(name, type);
	}

	public final void set(T value) {
		onSet(value);
	}

	@SuppressWarnings("unchecked")
	public boolean cast(Object value) {
		try {
			if (getType().isInstance(value)) {
				set((T) value);
				return true;
			}
		} catch (Throwable t) {}
		return false;
	}

}
