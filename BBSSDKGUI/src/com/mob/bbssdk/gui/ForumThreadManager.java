package com.mob.bbssdk.gui;


import android.content.Context;

import com.mob.tools.utils.SharePrefrenceHelper;

import java.util.HashSet;
import java.util.Set;

public class ForumThreadManager {
	private static final String SHAREDPRE_NAME = "sharedpre_forumreadedset";
	private static final String SHAREDPRE_KEY_ITEM_READ = "item_read";

	private Context appContext;
	private static ForumThreadManager forumThreadManager;
	private Set<String> setThreadReaded;
	private SharePrefrenceHelper sharePrefrenceHelper;

	public static synchronized void init(Context context) {
		if(forumThreadManager != null) {
			return;
		}
		forumThreadManager = new ForumThreadManager(context);
	}

	public static ForumThreadManager getInstance(Context context) {
		if(context == null) {
			throw new IllegalArgumentException("Context can't be null!");
		}
		if (forumThreadManager == null) {
			init(context);
		}
		return forumThreadManager;
	}

	private ForumThreadManager(Context context) {
		appContext = context.getApplicationContext();
		sharePrefrenceHelper = new SharePrefrenceHelper(appContext);
		sharePrefrenceHelper.open(SHAREDPRE_NAME);
		setThreadReaded = loadReadedThreadId();
		if(setThreadReaded == null) {
			setThreadReaded = new HashSet<String>();
		}
	}

	private Set<String> loadReadedThreadId() {
		return (Set<String>)sharePrefrenceHelper.get(SHAREDPRE_KEY_ITEM_READ);
	}

	public void saveReaded() {
		sharePrefrenceHelper.put(SHAREDPRE_KEY_ITEM_READ, setThreadReaded);
	}

	public boolean isThreadReaded(long tid) {
		return setThreadReaded.contains(String.valueOf(tid));
	}

	public void addReadThreadId(long tid) {
		setThreadReaded.add(String.valueOf(tid));
	}
}
