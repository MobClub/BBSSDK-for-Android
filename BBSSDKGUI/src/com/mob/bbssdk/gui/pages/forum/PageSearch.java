package com.mob.bbssdk.gui.pages.forum;


import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mob.bbssdk.gui.helper.FileHelper;
import com.mob.bbssdk.gui.helper.StorageFile;
import com.mob.bbssdk.gui.pages.BasePage;
import com.mob.bbssdk.gui.views.pullrequestview.SearchPullToRequestView;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ResHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageSearch extends BasePage {

	protected EditText editTextSearch;
	protected ImageView imageViewClear;
	protected TextView textViewCancel;
	protected String strKey;
	protected SearchPullToRequestView pullRequestView;
	protected ListView listViewSearchHistory;
	protected SearchHistoryAdapter searchHistoryAdapter;
	protected List<SearchHistoryItem> listSearchHistory;
	protected FrameLayout layoutListViewContainer;
	protected FileHelper fileHelperHistory;

	@Override
	protected View onCreateView(Context ctx) {
		Context context = getContext();
		View view = LayoutInflater.from(context).inflate(
				ResHelper.getLayoutRes(getContext(), "bbs_page_forum_search"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		editTextSearch = (EditText) contentView.findViewById(getIdRes("editTextSearch"));
		imageViewClear = (ImageView) contentView.findViewById(getIdRes("imageViewClear"));
		textViewCancel = (TextView) contentView.findViewById(getIdRes("textViewCancel"));
		pullRequestView = (SearchPullToRequestView) contentView.findViewById(getIdRes("pullRequestView"));
		listViewSearchHistory = (ListView) contentView.findViewById(getIdRes("listViewSearchHistory"));
		layoutListViewContainer = (FrameLayout) contentView.findViewById(getIdRes("layoutListViewContainer"));
		//回车时搜索
//		editTextSearch.setImeActionLabel("", KeyEvent.KEYCODE_ENTER);
//		editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				return false;
//			}
//		});
		//当有按键时显示搜索历史列表
		editTextSearch.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_UP) {
					if (KeyEvent.KEYCODE_ENTER == keyCode) {
						String text = editTextSearch.getText().toString();
						if (!StringUtils.isEmpty(text)) {
							search(text);
						}
						layoutListViewContainer.setVisibility(View.GONE);
						return false;
					} else {
						layoutListViewContainer.setVisibility(View.VISIBLE);
					}
				}
				return false;
			}
		});
		editTextSearch.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (MotionEvent.ACTION_UP == event.getAction()) {
					layoutListViewContainer.setVisibility(View.VISIBLE);
				}
				return false;
			}
		});
		editTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					layoutListViewContainer.setVisibility(View.GONE);
				} else {
					layoutListViewContainer.setVisibility(View.VISIBLE);
				}
			}
		});
		layoutListViewContainer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				layoutListViewContainer.setVisibility(View.GONE);
			}
		});

		imageViewClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editTextSearch.setText("");
			}
		});
		textViewCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		listSearchHistory = new ArrayList<SearchHistoryItem>();
		searchHistoryAdapter = new SearchHistoryAdapter(listSearchHistory);
		listViewSearchHistory.setAdapter(searchHistoryAdapter);
		layoutListViewContainer.setVisibility(View.VISIBLE);

		fileHelperHistory = new FileHelper(StorageFile.SearchHistory);
		loadFromStorage();
		searchHistoryAdapter.notifyDataSetChanged();
	}

	protected void loadFromStorage() {
		List<SearchHistoryItem> list = fileHelperHistory.readObj();
		if (list != null) {
			listSearchHistory.addAll(list);
		}
	}

	protected void saveToStorage() {
		fileHelperHistory.saveObj(listSearchHistory);
	}

	protected void search(String key) {
		strKey = key;
		pullRequestView.setKeywords(key);
		pullRequestView.performPullingDown(true);
		layoutListViewContainer.setVisibility(View.GONE);

		SearchHistoryItem item = new SearchHistoryItem();
		item.key = key;
		//If found the key in previous search history list, remove it first.
		int foundpos = -1;
		for (int i = 0; i < listSearchHistory.size(); i++) {
			if (listSearchHistory.get(i).equals(key)) {
				foundpos = i;
				break;
			}
		}
		if (foundpos != -1) {
			listSearchHistory.remove(foundpos);
		}
		listSearchHistory.add(0, item);

		searchHistoryAdapter.notifyDataSetChanged();
		saveToStorage();
	}

	class SearchHistoryAdapter extends BaseAdapter {
		List<SearchHistoryItem> listData;
		LayoutInflater layoutInflater;

		public SearchHistoryAdapter(List<SearchHistoryItem> list) {
			this.listData = list;
			layoutInflater = LayoutInflater.from(getContext());
		}

		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = layoutInflater.inflate(getLayoutId("bbs_item_searchhistory"), parent, false);
			}
			TextView textViewTitle = (TextView) convertView.findViewById(getIdRes("textViewTitle"));
			ImageView imageViewClose = (ImageView) convertView.findViewById(getIdRes("imageViewClose"));
			//hide the divider at the last one.
			View viewDivider = convertView.findViewById(getIdRes("viewDivider"));
			if(position == (getCount() - 1)) {
				viewDivider.setVisibility(View.GONE);
			} else {
				viewDivider.setVisibility(View.VISIBLE);
			}

			final SearchHistoryItem item = (SearchHistoryItem) getItem(position);
			textViewTitle.setText(item.key);
			imageViewClose.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					listData.remove(position);
					saveToStorage();
					notifyDataSetChanged();
				}
			});
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					editTextSearch.setText(item.key);
					search(item.key);
				}
			});
			return convertView;
		}
	}

	static class SearchHistoryItem implements Serializable {
		public String key;
	}
}
