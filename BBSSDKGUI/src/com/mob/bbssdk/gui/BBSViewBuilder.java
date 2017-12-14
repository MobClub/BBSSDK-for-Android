package com.mob.bbssdk.gui;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mob.MobSDK;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.dialog.ReplyEditorPopWindow;
import com.mob.bbssdk.gui.pages.BasePage;
import com.mob.bbssdk.gui.pages.PageWeb;
import com.mob.bbssdk.gui.pages.account.PageLogin;
import com.mob.bbssdk.gui.pages.account.PageReactiveConfirm;
import com.mob.bbssdk.gui.pages.account.PageRegister;
import com.mob.bbssdk.gui.pages.account.PageRegisterConfirm;
import com.mob.bbssdk.gui.pages.account.PageRetrievePassword;
import com.mob.bbssdk.gui.pages.account.PageRetrievePasswordConfirm;
import com.mob.bbssdk.gui.pages.forum.PageAttachmentViewer;
import com.mob.bbssdk.gui.pages.forum.PageForumThread;
import com.mob.bbssdk.gui.pages.forum.PageForumThreadDetail;
import com.mob.bbssdk.gui.pages.forum.PageImageViewer;
import com.mob.bbssdk.gui.pages.forum.PageMain;
import com.mob.bbssdk.gui.pages.forum.PageReportAccusation;
import com.mob.bbssdk.gui.pages.forum.PageSearch;
import com.mob.bbssdk.gui.pages.forum.PageSelectForum;
import com.mob.bbssdk.gui.pages.forum.PageWriteThread;
import com.mob.bbssdk.gui.pages.misc.PageFavorites;
import com.mob.bbssdk.gui.pages.misc.PageFollowers;
import com.mob.bbssdk.gui.pages.misc.PageFollowings;
import com.mob.bbssdk.gui.pages.misc.PageHistory;
import com.mob.bbssdk.gui.pages.misc.PageMessageDetails;
import com.mob.bbssdk.gui.pages.misc.PageMessages;
import com.mob.bbssdk.gui.pages.misc.PagePosts;
import com.mob.bbssdk.gui.pages.profile.PageEditSignature;
import com.mob.bbssdk.gui.pages.profile.PageInitProfile;
import com.mob.bbssdk.gui.pages.profile.PageOtherUserProfile;
import com.mob.bbssdk.gui.pages.profile.PageUserProfile;
import com.mob.bbssdk.gui.pages.profile.PageUserProfileDetails;
import com.mob.bbssdk.gui.views.ForumImageViewer;
import com.mob.bbssdk.gui.views.ForumThreadDetailView;
import com.mob.bbssdk.model.User;
import com.mob.tools.utils.ResHelper;


public class BBSViewBuilder {
	private static final String TAG = "bbsViewBuilder";
	private static BBSViewBuilder bbsViewBuilder;

	//	private HashMap<Class<? extends BasePage>, Class<? extends BasePage>> typeMap;
	public static synchronized void init(BBSViewBuilder viewbuilder) {
		if (bbsViewBuilder != null) {
//			throw new IllegalAccessError("You can only init bbsViewBuilder once!");
		}
		if (viewbuilder == null) {
			bbsViewBuilder = new BBSViewBuilder();
		} else {
			bbsViewBuilder = viewbuilder;
		}
	}

	public static BBSViewBuilder getInstance() {
		if (bbsViewBuilder == null) {
			init(null);
		}
		return bbsViewBuilder;
	}

	public BBSViewBuilder() {
//		typeMap.put(PageAttachmentViewer.class, PageAttachmentViewer.class);
//		typeMap.put(PageForumThreadDetail.class, PageForumThreadDetail.class);
//		typeMap.put(PageForumThread.class, PageForumThread.class);
//		typeMap.put(PageImageViewer.class, PageImageViewer.class);
//		typeMap.put(PageMain.class, PageMain.class);
//		typeMap.put(PageSelectForum.class, PageSelectForum.class);
//		typeMap.put(PageWeb.class, PageWeb.class);
//		typeMap.put(PageWriteThread.class, PageWriteThread.class);
//		typeMap.put(PageInitProfile.class, PageInitProfile.class);
//		typeMap.put(PageLogin.class, PageLogin.class);
//		typeMap.put(PageReactiveConfirm.class, PageReactiveConfirm.class);
	}
//另外一种通过反射创建的方法。
//		public <T extends BasePage> T build(Class<T> t) {
//		try {
//			return t.newInstance();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} finally {
//			return null;
//		}
//	}
//	interface Createable {
//		void init();
//	}

	public PageInitProfile buildPageInitProfile() {
		return new PageInitProfile();
	}

	public Integer getMainActivityStatusBarColor(Context context) {
		return getStatusBarColor(context);
	}

	public Integer getMainActivityLayout(Context context) {
		return null;
	}

	/**
	 * 产生浏览附件的Viewer
	 *
	 * @return the page attachment viewer
	 */
	public PageAttachmentViewer buildPageAttachmentViewer() {
		return new PageAttachmentViewer();
	}

	/**
	 * 产生帖子详情的页面
	 *
	 * @return the page forum thread detail
	 */
	public PageForumThreadDetail buildPageForumThreadDetail() {
		return new PageForumThreadDetail();
	}

	/**
	 * 产生板块列表的页面
	 *
	 * @return the page forum thread
	 */
	public PageForumThread buildPageForumThread() {
		return new PageForumThread();
	}

	/**
	 * 产生Image的Viewer
	 *
	 * @return the page image viewer
	 */
	public PageImageViewer buildPageImageViewer() {
		return new PageImageViewer();
	}

	/**
	 * 产生主页面
	 *
	 * @return the page main
	 */
	public PageMain buildPageMain() {
		return new PageMain();
	}

	/**
	 * 产生论坛板块选择页面
	 *
	 * @return the page select forum
	 */
	public PageSelectForum buildPageSelectForum() {
		return new PageSelectForum();
	}

	/**
	 * 产生打开外链接的页面
	 *
	 * @return the page web
	 */
	public PageWeb buildPageWeb() {
		return new PageWeb();
	}

	/**
	 * 产生创建帖子的页面
	 *
	 * @return the page write thread
	 */
	public PageWriteThread buildPageWriteThread() {
		return new PageWriteThread();
	}

	/**
	 * 产生用户资料编辑页面
	 *
	 * @return the page edit profile
	 */
	public PageInitProfile buildPageEditProfile() {
		return new PageInitProfile();
	}

	/**
	 * 产生登录页面
	 *
	 * @return the page login
	 */
	public PageLogin buildPageLogin() {
		return new PageLogin();
	}

	/**
	 * 产生重新激活确认页面
	 *
	 * @return the page reactive confirm
	 */
	public PageReactiveConfirm buildPageReactiveConfirm() {
		return new PageReactiveConfirm();
	}

	/**
	 * 产生注册页面
	 *
	 * @return the page register
	 */
	public PageRegister buildPageRegister() {
		return new PageRegister();
	}

	/**
	 * 产生注册确认页面
	 *
	 * @return the page register confirm
	 */
	public PageRegisterConfirm buildPageRegisterConfirm() {
		return new PageRegisterConfirm();
	}

	/**
	 * 产生找回密码页面
	 *
	 * @return the page retrieve password
	 */
	public PageRetrievePassword buildPageRetrievePassword() {
		return new PageRetrievePassword();
	}

	/**
	 * 产生找回密码确认页面
	 *
	 * @return the page retrieve password confirm
	 */
	public PageRetrievePasswordConfirm buildPageRetrievePasswordConfirm() {
		return new PageRetrievePasswordConfirm();
	}

	/**
	 * @return
	 */
	public PageOtherUserProfile buildPageOtherUserProfile() {
		return new PageOtherUserProfile();
	}

	/**
	 * 产生用户资料页面
	 *
	 * @return the base page
	 */
	public BasePage buildPageUserProfile() {
		return new PageUserProfile();
	}

	public PageReportAccusation buildPageReportAccusation() {
		return new PageReportAccusation();
	}

	/**
	 * · * 产生回帖页面
	 *
	 * @param context       the context
	 * @param listener      the listener
	 * @param clicklistener the clicklistener
	 * @return the reply editor pop window
	 */
	public ReplyEditorPopWindow buildReplyEditorPopWindow(Context context
			, ReplyEditorPopWindow.OnConfirmClickListener listener
			, ReplyEditorPopWindow.OnImgAddClickListener clicklistener) {
		return new ReplyEditorPopWindow(context, listener, clicklistener);
	}

	/**
	 * 产生搜索页面
	 *
	 * @return
	 */
	public PageSearch buildPageSearch() {
		return new PageSearch();
	}

	public ForumThreadDetailView buildForumThreadView(Context context) {
		return new ForumThreadDetailView(context);
	}

	public ForumImageViewer buildForumImageViewer(Context context) {
		return new ForumImageViewer(context);
	}

	public PageFollowings buildPageFollowings() {
		return new PageFollowings();
	}

	public PageFollowers buildPageFollowers() {
		return new PageFollowers();
	}

	public PageUserProfileDetails buildPageUserProfileDetails() {
		return new PageUserProfileDetails();
	}

	public PageFavorites buildPageFavorites() {
		return new PageFavorites();
	}

	public PagePosts buildPagePosts() {
		return new PagePosts();
	}

	public PageHistory buildPageHistory() {
		return new PageHistory();
	}

	public PageMessageDetails buildPageMessageDetails() {
		return new PageMessageDetails();
	}

	public PageMessages buildPageMessages() {
		return new PageMessages();
	}

	public PageEditSignature buildPageEditSignature() {
		return new PageEditSignature();
	}

	public User ensureLogin(boolean gotologinpage) {
		User user = null;
		try {
			user = BBSSDK.getApi(UserAPI.class).getCurrentUser();
		} catch (Exception e) {
			user = null;
		}
		if (user == null) {
			if (gotologinpage) {
				PageLogin page = buildPageLogin();
				page.show(MobSDK.getContext());
			}
			return null;
		}
		return user;
	}

	public Integer getOtherUserProfilePullRequestViewLayout(Context context) {
		return null;
	}

	public View buildForumThreadDetailView(Context context, ViewGroup viewgroup) {
		return null;
	}

	public Integer getBBSPullToRequestView_EmptyViewDrawableId(Context context) {
		return null;
	}

	public Integer getBBSPullToRequestView_getErrorViewDrawableId(Context context) {
		return null;
	}

	public Integer getBBSPullToRequestView_getEmptyViewStrId(Context context) {
		return null;
	}

	public Integer getBBSPullToRequestView_getErrorViewStrId(Context context) {
		return null;
	}

	public int getStatusBarColor(Context context) {
		return context.getResources().getColor(ResHelper.getColorRes(context, "bbs_statusbar_bg"));
	}
}
