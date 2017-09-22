package com.mob.bbssdk.gui.other;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.mob.bbssdk.gui.utils.ImageDownloader;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ReflectHelper;
import com.mob.tools.utils.ResHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class BannerLayout extends RelativeLayout {

	private static final String TAG = "BannerLayout";

	private ViewPager pager;
	//指示器容器
	private LinearLayout indicatorContainer;

	private Drawable unSelectedDrawable;
	private Drawable selectedDrawable;
	private int nWhatAutoPlay = 1000;
	private boolean isAutoPlay = true;
	private int itemCount;
	private int selectedIndicatorColor = 0xffff0000;
	private int unSelectedIndicatorColor = 0x88888888;

	private Shape indicatorShape = Shape.oval;
	private int selectedIndicatorHeight = 6;
	private int selectedIndicatorWidth = 6;
	private int unSelectedIndicatorHeight = 6;
	private int unSelectedIndicatorWidth = 6;

	private Position indicatorPosition = Position.centerBottom;
	private int autoPlayDuration = 3000;
	private int scrollDuration = 1100;

	private int indicatorSpace = 3;
	private int indicatorMargin = 10;

	private enum Shape {
		rect, oval
	}

	private enum Position {
		centerBottom,
		rightBottom,
		leftBottom,
		centerTop,
		rightTop,
		leftTop
	}

	private OnBannerItemClickListener onBannerItemClickListener;

	private Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if (msg.what == nWhatAutoPlay) {
				if (pager != null) {
					pager.setCurrentItem(pager.getCurrentItem() + 1, true);
				}
				handler.sendEmptyMessageDelayed(nWhatAutoPlay, autoPlayDuration);
			}
			return false;
		}
	});

	public BannerLayout(Context context) {
		super(context);
		init(null, 0);
	}

	public BannerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr);
	}

	public int[] getStyleables(String name) {
		Context context = getContext();
		if (name == null || name.length() == 0) {
			throw new IllegalStateException("No valid styleables name provided!");
		}
		try {
			String className = ReflectHelper.importClass("com.mob.bbssdk.gui.R$styleable");
			Object res = ReflectHelper.getStaticField(className, name);
			if (res == null) {
				return new int[0];
			} else {
				if (res.getClass().isArray()) {
					return (int[]) ((int[]) res);
				} else {
					return new int[]{((Integer) res).intValue()};
				}
			}
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return null;
		}
	}

	public int getStyleable(String name) {
		Context context = getContext();
		if (name == null || name.length() == 0) {
			throw new IllegalStateException("No valid styleables name provided!");
		}
		try {
			String className = ReflectHelper.importClass("com.mob.bbssdk.gui.R$styleable");
			Object res = ReflectHelper.getStaticField(className, name);
			if (res == null) {
				return 0;
			} else {
				return (Integer) res;
			}
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return 0;
		}
	}

	private void init(AttributeSet attrs, int defStyle) {
		Context context = getContext();
		TypedArray array = getContext().obtainStyledAttributes(attrs, getStyleables("BannerLayoutStyle"), defStyle, 0);
		selectedIndicatorColor = array.getColor(getStyleable("BannerLayoutStyle_selectedIndicatorColor"), selectedIndicatorColor);
		unSelectedIndicatorColor = array.getColor(getStyleable("BannerLayoutStyle_unSelectedIndicatorColor"), unSelectedIndicatorColor);

		int shape = array.getInt(getStyleable("BannerLayoutStyle_indicatorShape"), Shape.oval.ordinal());
		for (Shape shape1 : Shape.values()) {
			if (shape1.ordinal() == shape) {
				indicatorShape = shape1;
				break;
			}
		}
		selectedIndicatorHeight = (int) array.getDimension(getStyleable("BannerLayoutStyle_selectedIndicatorHeight"), selectedIndicatorHeight);
		selectedIndicatorWidth = (int) array.getDimension(getStyleable("BannerLayoutStyle_selectedIndicatorWidth"), selectedIndicatorWidth);
		unSelectedIndicatorHeight = (int) array.getDimension(getStyleable("BannerLayoutStyle_unSelectedIndicatorHeight"), unSelectedIndicatorHeight);
		unSelectedIndicatorWidth = (int) array.getDimension(getStyleable("BannerLayoutStyle_unSelectedIndicatorWidth"), unSelectedIndicatorWidth);

		int position = array.getInt(getStyleable("BannerLayoutStyle_indicatorPosition"), Position.centerBottom.ordinal());
		for (Position position1 : Position.values()) {
			if (position == position1.ordinal()) {
				indicatorPosition = position1;
			}
		}
		indicatorSpace = (int) array.getDimension(getStyleable("BannerLayoutStyle_indicatorSpace"), indicatorSpace);
		indicatorMargin = (int) array.getDimension(getStyleable("BannerLayoutStyle_indicatorMargin"), indicatorMargin);
		autoPlayDuration = array.getInt(getStyleable("BannerLayoutStyle_autoPlayDuration"), autoPlayDuration);
		scrollDuration = array.getInt(getStyleable("BannerLayoutStyle_scrollDuration"), scrollDuration);
		isAutoPlay = array.getBoolean(getStyleable("BannerLayoutStyle_isAutoPlay"), isAutoPlay);
		array.recycle();

		//绘制未选中状态图形
		LayerDrawable unSelectedLayerDrawable;
		LayerDrawable selectedLayerDrawable;
		GradientDrawable unSelectedGradientDrawable;
		unSelectedGradientDrawable = new GradientDrawable();

		//绘制选中状态图形
		GradientDrawable selectedGradientDrawable;
		selectedGradientDrawable = new GradientDrawable();
		switch (indicatorShape) {
			case rect: {
				unSelectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
				selectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
			}
			break;
			case oval: {
				unSelectedGradientDrawable.setShape(GradientDrawable.OVAL);
				selectedGradientDrawable.setShape(GradientDrawable.OVAL);
			}
			break;
		}
		unSelectedGradientDrawable.setColor(unSelectedIndicatorColor);
		unSelectedGradientDrawable.setSize(unSelectedIndicatorWidth, unSelectedIndicatorHeight);
		unSelectedLayerDrawable = new LayerDrawable(new Drawable[]{unSelectedGradientDrawable});
		unSelectedDrawable = unSelectedLayerDrawable;

		selectedGradientDrawable.setColor(selectedIndicatorColor);
		selectedGradientDrawable.setSize(selectedIndicatorWidth, selectedIndicatorHeight);
		selectedLayerDrawable = new LayerDrawable(new Drawable[]{selectedGradientDrawable});
		selectedDrawable = selectedLayerDrawable;

	}

	//添加本地图片路径
	public void setViewRes(List<Integer> viewRes) {
		List<View> views = new ArrayList<View>();
		itemCount = viewRes.size();
		//主要是解决当item为小于3个的时候滑动有问题，这里将其拼凑成3个以上
		if (itemCount < 1) {//当item个数0
			throw new IllegalStateException("item count not equal zero");
		} else if (itemCount < 2) {//当item个数为1
			views.add(getImageView(viewRes.get(0), 0));
			views.add(getImageView(viewRes.get(0), 0));
			views.add(getImageView(viewRes.get(0), 0));
		} else if (itemCount < 3) {//当item个数为2
			views.add(getImageView(viewRes.get(0), 0));
			views.add(getImageView(viewRes.get(1), 1));
			views.add(getImageView(viewRes.get(0), 0));
			views.add(getImageView(viewRes.get(1), 1));
		} else {
			for (int i = 0; i < viewRes.size(); i++) {
				views.add(getImageView(viewRes.get(i), i));
			}
		}
		setViews(views);
	}

	@NonNull
	private ImageView getImageView(Integer res, final int position) {
		ImageView imageView = new ImageView(getContext());
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onBannerItemClickListener != null) {
					onBannerItemClickListener.onItemClick(position);
				}
			}
		});
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        Glide.with(getContext()).load(res).centerCrop().into(imageView);
		imageView.setImageResource(res);
		return imageView;
	}

	//添加网络图片路径
	public void setViewItems(List<Item> urls) {
		List<View> views = new ArrayList<View>();
		itemCount = urls.size();
		//主要是解决当item为小于3个的时候滑动有问题，这里将其拼凑成3个以上
		if (itemCount < 1) {//当item个数0
//			Log.w(TAG, "itemCount is zero" + Log.getStackTraceString(new Throwable()));
			return;
		} else if (itemCount < 2) { //当item个数为1
			views.add(getImageView(urls.get(0), 0));
			views.add(getImageView(urls.get(0), 0));
			views.add(getImageView(urls.get(0), 0));
		} else if (itemCount < 3) {//当item个数为2
			views.add(getImageView(urls.get(0), 0));
			views.add(getImageView(urls.get(1), 1));
			views.add(getImageView(urls.get(0), 0));
			views.add(getImageView(urls.get(1), 1));
		} else {
			for (int i = 0; i < urls.size(); i++) {
				views.add(getImageView(urls.get(i), i));
			}
		}
		setViews(views);
	}

	@NonNull
	private View getImageView(final Item item, final int position) {
//		ImageView imageView = new ImageView(getContext());
//		imageView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (onBannerItemClickListener != null) {
//					onBannerItemClickListener.onItemClick(position);
//				}
//			}
//		});
//		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//		FrameLayout frameLayout = new FrameLayout(getContext());
//		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		frameLayout.addView(imageView, params);
//
//		if(!StringUtils.isEmpty(item.strTitle)) {
//			TextView textview = new TextView(getContext());
//			textview.setText(item.strTitle);
//			textview.setTextColor(Color.WHITE);
//			textview.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenUtils.dpToPx(20));
//			textview.setMaxLines(2);
//			textview.setEllipsize(TextUtils.TruncateAt.END);
//
//			params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			params.leftMargin = ScreenUtils.dpToPx(20);
//			params.rightMargin = ScreenUtils.dpToPx(20);
//			params.bottomMargin = ScreenUtils.dpToPx(40);
//			params.gravity = Gravity.BOTTOM;
//			frameLayout.addView(textview, params);
//		}
//        Glide.with(getContext()).load(url).centerCrop().into(imageView);
//		ImageDownloader.loadImage(item.strUrl, imageView);
//		return frameLayout;

		View view = LayoutInflater.from(getContext()).inflate(ResHelper.getLayoutRes(getContext(), "bbs_item_banner"), null);
		ImageView imageViewContent = (ImageView) view.findViewById(ResHelper.getIdRes(getContext(), "imageViewContent"));
		ImageDownloader.loadImage(item.strUrl, imageViewContent);

		TextView textViewTitle = (TextView) view.findViewById(ResHelper.getIdRes(getContext(), "textViewTitle"));
		if (!StringUtils.isEmpty(item.strTitle)) {
			textViewTitle.setText(item.strTitle);
		}
		imageViewContent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onBannerItemClickListener != null) {
					onBannerItemClickListener.onItemClick(position);
				}
			}
		});
		return view;
	}

	//添加任意View视图
	private void setViews(final List<View> views) {
		//初始化pager
		pager = new ViewPager(getContext());
		//添加viewpager到SliderLayout
		addView(pager, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		setSliderTransformDuration(scrollDuration);
		//初始化indicatorContainer
		indicatorContainer = new LinearLayout(getContext());
		indicatorContainer.setGravity(Gravity.CENTER_VERTICAL);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		switch (indicatorPosition) {
			case centerBottom: {
				params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			}
			break;
			case centerTop: {
				params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			}
			break;
			case leftBottom: {
				params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			}
			break;
			case leftTop: {
				params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			}
			break;
			case rightBottom: {
				params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			}
			break;
			case rightTop: {
				params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			}
			break;
		}
		//设置margin
		params.setMargins(indicatorMargin, indicatorMargin, indicatorMargin, indicatorMargin);
		//添加指示器容器布局到SliderLayout
		addView(indicatorContainer, params);

		//初始化指示器，并添加到指示器容器布局
		for (int i = 0; i < itemCount; i++) {
			ImageView indicator = new ImageView(getContext());
			indicator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			indicator.setPadding(indicatorSpace, indicatorSpace, indicatorSpace, indicatorSpace);
			indicator.setImageDrawable(unSelectedDrawable);
			indicatorContainer.addView(indicator);
		}
		LoopPagerAdapter pagerAdapter = new LoopPagerAdapter(views);
		pager.setAdapter(pagerAdapter);
		//设置当前item到Integer.MAX_VALUE中间的一个值，看起来像无论是往前滑还是往后滑都是ok的
		//如果不设置，用户往左边滑动的时候已经划不动了
		int targetItemPosition = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % itemCount;
		pager.setCurrentItem(targetItemPosition);
		switchIndicator(targetItemPosition % itemCount);
		pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				switchIndicator(position % itemCount);
			}
		});
		startAutoPlay();
	}

	public void setSliderTransformDuration(int duration) {
		try {
			Field fscroller = ViewPager.class.getDeclaredField("mScroller");
			fscroller.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(pager.getContext(), null, duration);
			fscroller.set(pager, scroller);
		} catch (Exception e) {
		}
	}

	/**
	 * 开始自动轮播
	 */
	public void startAutoPlay() {
		stopAutoPlay(); // 避免重复消息
		if (isAutoPlay) {
			handler.sendEmptyMessageDelayed(nWhatAutoPlay, autoPlayDuration);
		}
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		if (visibility == VISIBLE) {
			startAutoPlay();
		} else {
			stopAutoPlay();
		}
	}


	/**
	 * 停止自动轮播
	 */
	public void stopAutoPlay() {
		if (isAutoPlay) {
			handler.removeMessages(nWhatAutoPlay);
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(pager == null || pager.getAdapter() == null || pager.getAdapter().getCount() == 0) {
			return false;
		}
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				stopAutoPlay();
			}
			break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP: {
				startAutoPlay();
			}
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 切换指示器状态
	 *
	 * @param currentPosition 当前位置
	 */
	private void switchIndicator(int currentPosition) {
		for (int i = 0; i < indicatorContainer.getChildCount(); i++) {
			((ImageView) indicatorContainer.getChildAt(i)).setImageDrawable(i == currentPosition ? selectedDrawable : unSelectedDrawable);
		}
	}


	public void setOnBannerItemClickListener(OnBannerItemClickListener onBannerItemClickListener) {
		this.onBannerItemClickListener = onBannerItemClickListener;
	}

	public interface OnBannerItemClickListener {
		void onItemClick(int position);
	}

	public class LoopPagerAdapter extends PagerAdapter {
		private List<View> views;

		public LoopPagerAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public int getCount() {
			//Integer.MAX_VALUE = 2147483647
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			if (views.size() > 0) {
				//position % view.size()是指虚拟的position会在[0，view.size()）之间循环
				View view = views.get(position % views.size());
				if (container.equals(view.getParent())) {
					container.removeView(view);
				}
				container.addView(view);
				return view;
			}
			return null;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

		}
	}

	public class FixedSpeedScroller extends Scroller {

		private int nDuration = 1000;

		public FixedSpeedScroller(Context context) {
			super(context);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator, int duration) {
			this(context, interpolator);
			nDuration = duration;
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy, int duration) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, nDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, nDuration);
		}
	}

	public static class Item {
		public String strUrl;
		public String strTitle;

		public Item() {
		}

		public Item(String url, String title) {
			this.strUrl = url;
			this.strTitle = title;
		}
	}
}


