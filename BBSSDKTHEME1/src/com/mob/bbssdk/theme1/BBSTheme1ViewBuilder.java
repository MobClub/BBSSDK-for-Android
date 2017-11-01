package com.mob.bbssdk.theme1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.dialog.ReplyEditorPopWindow;
import com.mob.bbssdk.gui.pages.BasePage;
import com.mob.bbssdk.gui.pages.account.PageLogin;
import com.mob.bbssdk.gui.pages.account.PageReactiveConfirm;
import com.mob.bbssdk.gui.pages.account.PageRegister;
import com.mob.bbssdk.gui.pages.account.PageRegisterConfirm;
import com.mob.bbssdk.gui.pages.account.PageRetrievePassword;
import com.mob.bbssdk.gui.pages.account.PageRetrievePasswordConfirm;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.pages.forum.PageReportAccusation;
import com.mob.bbssdk.gui.pages.forum.PageSearch;
import com.mob.bbssdk.gui.pages.forum.PageWriteThread;
import com.mob.bbssdk.gui.pages.misc.PageFollowers;
import com.mob.bbssdk.gui.pages.misc.PageFollowings;
import com.mob.bbssdk.gui.pages.misc.PageMessageDetails;
import com.mob.bbssdk.gui.pages.misc.PageMessages;
import com.mob.bbssdk.gui.pages.profile.PageInitProfile;
import com.mob.bbssdk.gui.pages.profile.PageOtherUserProfile;
import com.mob.bbssdk.gui.pages.profile.PageUserProfileDetails;
import com.mob.bbssdk.theme0.page.user.Theme0PageFollowers;
import com.mob.bbssdk.theme0.page.user.Theme0PageFollowings;
import com.mob.bbssdk.theme0.page.user.Theme0PageMessages;
import com.mob.bbssdk.theme0.page.user.Theme0PageUserProfileDetails;
import com.mob.bbssdk.theme1.page.forum.Theme1PageForumThreadDetail;
import com.mob.bbssdk.theme1.page.forum.Theme1PageReportAccusation;
import com.mob.bbssdk.theme1.page.forum.Theme1PageWriteThread;
import com.mob.bbssdk.theme1.page.user.Theme1PageInitProfile;
import com.mob.bbssdk.theme1.page.user.Theme1PageLogin;
import com.mob.bbssdk.theme1.page.user.Theme1PageOtherUserProfile;
import com.mob.bbssdk.theme1.page.user.Theme1PageReactiveConfirm;
import com.mob.bbssdk.theme1.page.user.Theme1PageRegister;
import com.mob.bbssdk.theme1.page.user.Theme1PageRegisterConfirm;
import com.mob.bbssdk.theme1.page.user.Theme1PageRetrievePassword;
import com.mob.bbssdk.theme1.page.user.Theme1PageRetrievePasswordConfirm;
import com.mob.bbssdk.theme1.page.user.Theme1PageUserProfile;
import com.mob.tools.utils.ResHelper;

public class BBSTheme1ViewBuilder extends BBSViewBuilder {

	@Override
	public PageLogin buildPageLogin() {
		return new Theme1PageLogin();
	}

	@Override
	public PageInitProfile buildPageEditProfile() {
		return new Theme1PageInitProfile();
	}

	@Override
	public PageInitProfile buildPageInitProfile() {
		return new Theme1PageInitProfile();
	}

	@Override
	public PageRegister buildPageRegister() {
		return new Theme1PageRegister();
	}

	@Override
	public PageRetrievePassword buildPageRetrievePassword() {
		return new Theme1PageRetrievePassword();
	}

	@Override
	public PageReactiveConfirm buildPageReactiveConfirm() {
		return new Theme1PageReactiveConfirm();
	}

	@Override
	public PageRegisterConfirm buildPageRegisterConfirm() {
		return new Theme1PageRegisterConfirm();
	}

	@Override
	public PageRetrievePasswordConfirm buildPageRetrievePasswordConfirm() {
		return new Theme1PageRetrievePasswordConfirm();
	}

	@Override
	public BasePage buildPageUserProfile() {
		//先跳转到个人中心Theme0PagePersonalInfo 然后可以再跳转到Theme0PageUserProfile
		return new Theme1PageUserProfile();
	}

	@Override
	public PageForumThreadDetail buildPageForumThreadDetail() {
		return new Theme1PageForumThreadDetail();
	}

	@Override
	public PageOtherUserProfile buildPageOtherUserProfile() {
		return new Theme1PageOtherUserProfile();
	}

	@Override
	public Integer getMainActivityLayout(Context context) {
		return ResHelper.getLayoutRes(context, "bbs_theme1_activity_main");
	}

	@Override
	public PageReportAccusation buildPageReportAccusation() {
		return new Theme1PageReportAccusation();
	}

	public PageMessageDetails buildPageMessageDetails() {
		return new PageMessageDetails() {
			@Override
			protected View onCreateContentView(Context context) {
				View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme1_messagedetails"), null);
				return view;
			}
		};
	}

	public PageFollowings buildPageFollowings() {
		return new Theme0PageFollowings();
	}

	public PageFollowers buildPageFollowers() {
		return new Theme0PageFollowers();
	}

	@Override
	public PageMessages buildPageMessages() {
		return new Theme0PageMessages() {
			@Override
			protected View onCreateContentView(Context context) {
				View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme1_messages"), null);
				return view;
			}
		};
	}

	@Override
	public PageUserProfileDetails buildPageUserProfileDetails() {
		return new Theme0PageUserProfileDetails() {
			@Override
			protected void onViewCreated(View contentView) {
				super.onViewCreated(contentView);
			}
		};
	}

	@Override
	public PageWriteThread buildPageWriteThread() {
		return new Theme1PageWriteThread();
	}

	public View buildForumThreadDetailView(Context context, ViewGroup viewgroup) {
		return null;
	}

	@Override
	public ReplyEditorPopWindow buildReplyEditorPopWindow(Context context
			, ReplyEditorPopWindow.OnConfirmClickListener listener
			, ReplyEditorPopWindow.OnImgAddClickListener clicklistener) {
		return new ReplyEditorPopWindow(context, listener, clicklistener) {
			@Override
			protected View getContentView() {
				return LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_theme1_reply_editor"), null);
			}
			@Override
			protected Integer getAddPicImageId() {
				return ResHelper.getBitmapRes(getContext(), "bbs_theme1_reply_addpic");
			}
			@Override
			protected Integer getReplyAddPicLayoutId() {
				return ResHelper.getLayoutRes(getContext(), "bbs_theme1_reply_addpic");
			}
		};
	}

	@Override
	public Integer getBBSPullToRequestView_EmptyViewDrawableId(Context context) {
		return ResHelper.getBitmapRes(context, "bbs_theme1_pullrequestview_empty");
	}

	@Override
	public Integer getBBSPullToRequestView_getErrorViewDrawableId(Context context) {
		return null;
	}

	@Override
	public Integer getBBSPullToRequestView_getEmptyViewStrId(Context context) {
		return ResHelper.getStringRes(context, "bbs_theme1_pullrequestview_empty");
	}

	@Override
	public Integer getBBSPullToRequestView_getErrorViewStrId(Context context) {
		return null;
	}

	@Override
	public int getStatusBarColor(Context context) {
		return context.getResources().getColor(ResHelper.getColorRes(context, "bbs_theme1_statusbar_grey"));
	}

	@Override
	public Integer getMainActivityStatusBarColor(Context context) {
		return getStatusBarColor(context);
	}

	@Override
	public PageSearch buildPageSearch() {
		return new PageSearch() {
			@Override
			protected View onCreateView(Context ctx) {
				Context context = getContext();
				View view = LayoutInflater.from(context).inflate(
						ResHelper.getLayoutRes(getContext(), "bbs_theme1_pagesearch"), null);
				return view;
			}
		};
	}
}
