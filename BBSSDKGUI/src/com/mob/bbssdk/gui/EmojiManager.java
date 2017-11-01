package com.mob.bbssdk.gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mob.MobSDK;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.Hashon;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 *
 */

public class EmojiManager {

	public enum EmojiClass {
		Default("default"),
		CoolMonkey("coolmonkey"),
		Grapman("grapeman");

		private String strKey;

		EmojiClass(String key) {
			this.strKey = key;
		}

		public String getKey() {
			return strKey;
		}

		public static EmojiClass fromString(String key) {
			if(key == null || StringUtils.isEmpty(key)) {
				return null;
			}
			if(Default.getKey().equals(key)) {
				return Default;
			} else if(CoolMonkey.getKey().equals(key)) {
				return CoolMonkey;
			} else if(Grapman.getKey().equals(key)) {
				return Grapman;
			}
			return null;
		}
	}

	private static EmojiManager emojiManager;
	private Map<String, String> mapEmoji = new HashMap<String, String>();//key path
	private Map<String, String> mapEmojiReversed = new HashMap<String, String>();//path key
	private List<Map.Entry<String, String>> listEmoji;//sorted map entry
	private Map<EmojiClass, List<String>> mapEmojiType = new HashMap<EmojiClass, List<String>>();
	private static final String EMOJI_DIR = "bbssdk/emoji/";

	public static synchronized void init() {
		if (emojiManager != null) {
			return;
		}
		emojiManager = new EmojiManager();
	}

	public static EmojiManager getInstance() {
		if (emojiManager == null) {
			init();
		}
		return emojiManager;
	}

	private EmojiManager() {
		loadEmojiMap();
		splitEmojiMap();
	}

	public boolean setGifImageView(GifImageView gifimageview, String key) {
		if (gifimageview == null || StringUtils.isEmpty(key)) {
			return false;
		}
		GifDrawable drawable = null;
		try {
			drawable = new GifDrawable(MobSDK.getContext().getAssets(),
					EmojiManager.getInstance().getEmojiDir(key));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (drawable != null) {
			gifimageview.setImageDrawable(drawable);
			return true;
		}
		return false;
	}

	public Bitmap getEmoji(String key) {
		String dir = getEmojiDir(key);
		if (StringUtils.isEmpty(dir)) {
			return null;
		}
		InputStream is = null;
		try {
			is = MobSDK.getContext().getResources().getAssets().open(dir);
			return BitmapFactory.decodeStream(is);
		} catch (IOException e) {
		}
		return null;
	}

	public String getEmojiDir(String key) {
		if (StringUtils.isEmpty(key) || mapEmoji == null || mapEmoji.size() == 0) {
			return null;
		}
		String dir = mapEmoji.get(key);
		if (StringUtils.isEmpty(dir)) {
			return null;
		}
		return EMOJI_DIR + dir;
	}

	protected void loadEmojiMap() {
		try {
			InputStream is = MobSDK.getContext().getAssets().open("bbssdk/emoji.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			String text = new String(buffer);
			Map<String, String> mapfromjson = new Hashon().fromJson(text);

			listEmoji = new ArrayList<Map.Entry<String, String>>(mapfromjson.entrySet());
			Collections.sort(listEmoji, new Comparator<Map.Entry<String, String>>() {
				@Override
				public int compare(Map.Entry<String, String> lhs, Map.Entry<String, String> rhs) {
					return lhs.getValue().split("\\/")[0].compareTo(rhs.getValue().split("\\/")[0]);
				}
			});
			for (Map.Entry<String, String> entry : listEmoji) {
				mapEmoji.put(entry.getKey(), entry.getValue());
				mapEmojiReversed.put(entry.getValue(), entry.getKey());
			}
		} catch (IOException e) {
			// Should never happen!
			throw new RuntimeException(e);
		}
	}

	public Map<EmojiClass, List<String>> getMapEmojiType() {
		return mapEmojiType;
	}

	protected void splitEmojiMap() {
		List<String> listcurrent = new ArrayList<String>();
		String valuecurrent;
		String valuelast = null;
		for (Map.Entry<String, String> entry : listEmoji) {
			valuecurrent = entry.getValue().split("\\/")[0];
			if (valuelast == null || valuelast.equals(valuecurrent)) {
				listcurrent.add(entry.getKey());
			} else {
				listcurrent = new ArrayList<String>();//create new list;
				listcurrent.add(entry.getKey());
			}
			EmojiClass emojiclass = EmojiClass.fromString(valuecurrent);
			if(emojiclass != null) {
				if(mapEmojiType.get(emojiclass) == null) {
					mapEmojiType.put(emojiclass, listcurrent);
				}
			}
			valuelast = valuecurrent;
		}
	}
}
