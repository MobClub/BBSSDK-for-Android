package com.mob.bbssdk.gui.views;

import com.mob.bbssdk.gui.EmojiManager;

/**
 *
 */

public enum EmojiTab {
	General(EmojiManager.EmojiClass.Default),
	Grapeman(EmojiManager.EmojiClass.Grapman),
	CoolMonkey(EmojiManager.EmojiClass.CoolMonkey);

	private EmojiManager.EmojiClass clz;

	EmojiTab(EmojiManager.EmojiClass cls) {
		this.clz = cls;
	}

	public EmojiManager.EmojiClass getEmojiClass() {
		return clz;
	}

	public static EmojiTab fromPosition(int pos) {
		if (pos == 0) {
			return General;
		} else if (pos == 1) {
			return Grapeman;
		} else if (pos == 2) {
			return CoolMonkey;
		} else {
			return General;
		}
	}
}
