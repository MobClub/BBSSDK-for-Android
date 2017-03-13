package com.mob.bbssdk.gui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mob.bbssdk.gui.ptrlistview.BasePagedItemAdapter;
import com.mob.bbssdk.model.ForumThread;
import com.mob.tools.gui.AsyncImageView;
import com.mob.tools.gui.PullToRequestView;
import com.mob.tools.utils.ResHelper;

import java.text.SimpleDateFormat;

public abstract class ForumThreadItemAdapter extends BasePagedItemAdapter<ForumThread> {
	private int defaultPicResId;
	private int errorPicResId;

	public ForumThreadItemAdapter(PullToRequestView view) {
		super(view);
		defaultPicResId = ResHelper.getBitmapRes(getContext(), "bbs_ic_def_no_pic");
		errorPicResId = ResHelper.getBitmapRes(getContext(), "bbs_ic_def_error_pic");
	}

	protected int getEmptyViewDrawableId() {
		return ResHelper.getBitmapRes(getContext(), "bbs_ic_def_no_data");
	}

	protected int getErrorViewDrawableId() {
		return ResHelper.getBitmapRes(getContext(), "bbs_ic_def_no_net");
	}

	protected int getEmptyViewStrId() {
		return ResHelper.getStringRes(getContext(), "bbs_empty_view_str");
	}

	protected int getErrorViewStrId() {
		return ResHelper.getStringRes(getContext(), "bbs_error_view_str");
	}

	public View getContentView(int position, View convertView, ViewGroup viewGroup) {
		View view = convertView;
		final ViewHolder viewHolder;
		if (view == null) {
			view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_forum_thread_item"), viewGroup, false);
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		ForumThread thread = getItem(position);
		if (thread != null) {
			TextView tvTitle = viewHolder.getView(ResHelper.getIdRes(getContext(), "tvTitle"));
			if (tvTitle != null) {
				tvTitle.setText(thread.subject);
			}
			TextView tvContent = viewHolder.getView(ResHelper.getIdRes(getContext(), "tvContent"));
			if (tvContent != null) {
				tvContent.setText(thread.summary);
			}

			View llPic = viewHolder.getView(ResHelper.getIdRes(getContext(), "llPic"));

			if (llPic != null) {
				if (thread.images != null && thread.images.size() > 0) {
					llPic.setVisibility(View.VISIBLE);
					AsyncImageView aivPic1 = viewHolder.getView(ResHelper.getIdRes(getContext(), "aivPic1"));
					AsyncImageView aivPic2 = viewHolder.getView(ResHelper.getIdRes(getContext(), "aivPic2"));
					AsyncImageView aivPic3 = viewHolder.getView(ResHelper.getIdRes(getContext(), "aivPic3"));
					if (aivPic1 != null) {
						if (isFling()) {
							aivPic1.execute(null, defaultPicResId);
						} else {
							aivPic1.execute(thread.images.get(0), defaultPicResId, errorPicResId);
						}
					}
					if (aivPic2 != null) {
						if (thread.images.size() > 1) {
							if (isFling()) {
								aivPic2.execute(null, defaultPicResId);
							} else {
								aivPic2.execute(thread.images.get(1), defaultPicResId, errorPicResId);
							}
						} else {
							aivPic2.execute(null, null);
						}
					}
					if (aivPic3 != null) {
						if (thread.images.size() > 2) {
							if (isFling()) {
								aivPic3.execute(null, defaultPicResId);
							} else {
								aivPic3.execute(thread.images.get(2), defaultPicResId, errorPicResId);
							}
						} else {
							aivPic3.execute(null, null);
						}
					}
				} else {
					llPic.setVisibility(View.GONE);
				}
			}

			AsyncImageView aivAvatar = viewHolder.getView(ResHelper.getIdRes(getContext(), "aivAvatar"));
			if (aivAvatar != null) {
				int avatarSize = getContext().getResources().getDimensionPixelSize(ResHelper.getResId(getContext(), "dimen",
						"bbs_forum_thread_list_item_avatar_size"));
				aivAvatar.setRound(avatarSize / 2);
				aivAvatar.execute(thread.avatar, null);
			}

			TextView tvAuthor = viewHolder.getView(ResHelper.getIdRes(getContext(), "tvAuthor"));
			if (tvAuthor != null) {
				tvAuthor.setText(thread.author);
			}

			TextView tvTime = viewHolder.getView(ResHelper.getIdRes(getContext(), "tvTime"));
			if (tvTime != null) {
				tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(thread.createdOn * 1000));
			}

			TextView tvCommentCounts = viewHolder.getView(ResHelper.getIdRes(getContext(), "tvCommentCounts"));
			if (tvCommentCounts != null) {
				tvCommentCounts.setText(String.valueOf(thread.replies));
			}
		}

		return view;
	}
}
