package com.mob.bbssdk.gui;


import com.mob.bbssdk.gui.helper.FileHelper;
import com.mob.bbssdk.gui.helper.StorageFile;
import com.mob.bbssdk.model.ForumThread;

import java.util.ArrayList;
import java.util.List;

public class ForumThreadHistoryManager {

	private static ForumThreadHistoryManager forumThreadHistoryManager;
	private List<ForumThread> setThreadReaded;
	private FileHelper fileHelper;
	private boolean modifiedThreadReadedList = false;

	public static synchronized void init() {
		if (forumThreadHistoryManager != null) {
			return;
		}
		forumThreadHistoryManager = new ForumThreadHistoryManager();
	}

	public static ForumThreadHistoryManager getInstance() {
		if (forumThreadHistoryManager == null) {
			init();
		}
		return forumThreadHistoryManager;
	}

	private ForumThreadHistoryManager() {
		fileHelper = new FileHelper(StorageFile.ThreadReadedHistory);
		loadReadedThread();
		if (setThreadReaded == null) {
			setThreadReaded = new ArrayList<ForumThread>();
		}
	}

	private void loadReadedThread() {
		try {
			setThreadReaded = fileHelper.readObj();
		} catch (ClassCastException e) {
			e.printStackTrace();
			fileHelper.clearContent();
			setThreadReaded = new ArrayList<ForumThread>();
		}
	}

	public void clearReaded() {
		setThreadReaded.clear();
		fileHelper.saveObj(setThreadReaded);
		modifiedThreadReadedList = false;
	}

	public void saveReaded() {
		if (modifiedThreadReadedList) {
			fileHelper.saveObj(setThreadReaded);
			modifiedThreadReadedList = false;
		}
	}

	public boolean isThreadReaded(long tid) {
		for (ForumThread thread : setThreadReaded) {
			if (thread.tid == tid) {
				return true;
			}
		}
		return false;
	}

	public List<ForumThread> getReadedThread() {
		return setThreadReaded;
	}

	public boolean isThreadReaded(ForumThread thread) {
		return isThreadReaded(thread.tid);
	}

	public void removeReadeThread(ForumThread thread) {
		ForumThread founded = null;
		for (ForumThread item : setThreadReaded) {
			if (item.tid == thread.tid) {
				founded = item;
			}
		}
		if (founded != null) {
			setThreadReaded.remove(founded);
		}
		modifiedThreadReadedList = true;
	}

	public void addReadedThread(ForumThread thread) {
		removeReadeThread(thread);
		setThreadReaded.add(0, thread);
		modifiedThreadReadedList = true;
	}

	public int getReadedThreadCount() {
		if (setThreadReaded == null) {
			return 0;
		} else {
			return setThreadReaded.size();
		}
	}
}
