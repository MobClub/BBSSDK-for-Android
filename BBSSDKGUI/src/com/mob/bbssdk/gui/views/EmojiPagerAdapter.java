package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.mob.bbssdk.gui.EmojiManager;
import com.mob.bbssdk.gui.utils.ScreenUtils;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

/**
 *
 */

public class EmojiPagerAdapter extends PagerAdapter implements View.OnClickListener {
	private List<List<String>> emojiList = new ArrayList<List<String>>();
	private EditText editText;
	private View.OnClickListener onClickListener;
	private Context context;

	public EmojiPagerAdapter(Context context, View.OnClickListener listener) {
		this.onClickListener = listener;
		this.context = context;
		init();
	}

	public EmojiPagerAdapter(EditText editText) {
		this.editText = editText;
		this.context = editText.getContext();
		init();
	}

	protected void init() {
		emojiList = new ArrayList<List<String>>();
		Map<EmojiManager.EmojiClass, List<String>> mapemoji = EmojiManager.getInstance().getMapEmojiType();
		for (EmojiTab tab : EmojiTab.values()) {
			List<String> listkey = mapemoji.get(tab.getEmojiClass());
			if (listkey != null) {
				emojiList.add(listkey);
			}
		}
	}

	@Override
	public int getCount() {
		return emojiList == null ? 0 : emojiList.size();
	}

	@Override
	public void onClick(View v) {
		if(onClickListener == null) {
			if(editText != null) {
				String key = (String) v.getTag();
				editText.getText().insert(editText.getSelectionStart(), key);
			} else {
				//error
			}
		} else {
			onClickListener.onClick(v);
		}
	}

	@Override
	public Object instantiateItem(ViewGroup viewgroup, int position) {
		View view = (View) LayoutInflater.from(context).
				inflate(ResHelper.getLayoutRes(context, "bbs_replywindow_emojigrid"), viewgroup, false);
		GridView gridview =
				(GridView) view.findViewById(ResHelper.getIdRes(context, "expandableHeightGridView"));
		final List<String> listkey = emojiList.get(position);
		gridview.setAdapter(new BaseAdapter() {
			@Override
			public int getCount() {
				return listkey.size();
			}

			@Override
			public Object getItem(int position) {
				return listkey.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				GifImageView gifimageview = new GifImageView(context);
				gifimageview.setImageBitmap(EmojiManager.getInstance().getEmoji((String) getItem(position)));
				gifimageview.setTag(listkey.get(position));
				gifimageview.setOnClickListener(EmojiPagerAdapter.this);
				ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
				layoutParams.width = ScreenUtils.dpToPx(20);
				layoutParams.height = ScreenUtils.dpToPx(20);
				gifimageview.setLayoutParams(layoutParams);
				gifimageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
				return gifimageview;
			}
		});
		viewgroup.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}
