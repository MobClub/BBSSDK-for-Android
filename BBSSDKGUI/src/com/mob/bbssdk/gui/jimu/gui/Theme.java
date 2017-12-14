package com.mob.bbssdk.gui.jimu.gui;

import android.content.Context;

import com.mob.tools.MobLog;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

public abstract class Theme {
	private HashSet<Class<? extends PageAdapter<?>>> pageAdapters;
	private HashSet<Class<? extends DialogAdapter<?>>> dialogAdapters;

	public Theme() {
		pageAdapters = new HashSet<Class<? extends PageAdapter<?>>>();
		initPageAdapters(pageAdapters);
		dialogAdapters = new HashSet<Class<? extends DialogAdapter<?>>>();
		initDialogAdapters(dialogAdapters);
	}

	protected abstract void initPageAdapters(Set<Class<? extends PageAdapter<?>>>adapters);

	protected abstract void initDialogAdapters(Set<Class<? extends DialogAdapter<?>>> adapters);

	@SuppressWarnings("unchecked")
	public final <P extends Page<P>> PageAdapter<P> getPageAdapter(P page) {
		return (PageAdapter<P>) getAdapter(page, pageAdapters);
	}

	@SuppressWarnings("unchecked")
	public final <D extends Dialog<D>> DialogAdapter<D> getDialogAdapter(D dialog) {
		return (DialogAdapter<D>) getAdapter(dialog, dialogAdapters);
	}

	private <X, Adapter> Adapter getAdapter(X x, Set<Class<? extends Adapter>> adapters) {
		try {
			ParameterizedType type = (ParameterizedType) x.getClass().getGenericSuperclass();
			Class<?> xClass = (Class<?>) type.getActualTypeArguments()[0];
			for (Class<? extends Adapter> adapterClass : adapters) {
				type = (ParameterizedType) adapterClass.getGenericSuperclass();
				if (xClass.equals(type.getActualTypeArguments()[0])) {
					return (Adapter) adapterClass.newInstance();
				}
			}
		} catch (Throwable t) {
			MobLog.getInstance().w(t);
		}
		return null;
	}

	public abstract int getDialogStyle(Context context);

}
