package com.mob.bbssdk.gui.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.mob.bbssdk.gui.EmojiManager;
import com.mob.bbssdk.gui.utils.ScreenUtils;
import com.mob.bbssdk.gui.views.felipecsl.gifimageview.GifImageView;
import com.mob.tools.gui.ViewPagerAdapter;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 */

public class EmojiPagerAdapter extends ViewPagerAdapter implements View.OnClickListener {
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
	public View getView(int position, View view0, ViewGroup viewgroup) {
		View view = (View) LayoutInflater.from(context).
				inflate(ResHelper.getLayoutRes(context, "bbs_replywindow_emojigrid"), null, false);
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
				EmojiManager.setGifImageView(gifimageview, (String) getItem(position));
				gifimageview.setTag(listkey.get(position));
				gifimageview.setOnClickListener(EmojiPagerAdapter.this);
				ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ScreenUtils.dpToPx(20), ScreenUtils.dpToPx(20));
				gifimageview.setLayoutParams(layoutParams);
				gifimageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
				return gifimageview;
			}
		});
		return view;
	}
}
