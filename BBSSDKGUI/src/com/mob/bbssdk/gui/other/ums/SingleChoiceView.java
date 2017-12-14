package com.mob.bbssdk.gui.other.ums;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.MobSDK;
import com.mob.bbssdk.gui.jimu.gui.DialogAdapter;
import com.mob.bbssdk.gui.jimu.gui.Page;
import com.mob.bbssdk.gui.jimu.gui.PageAdapter;
import com.mob.bbssdk.gui.jimu.gui.Theme;
import com.mob.bbssdk.gui.other.ums.datatype.Area;
import com.mob.bbssdk.gui.other.ums.datatype.City;
import com.mob.bbssdk.gui.other.ums.datatype.Constellation;
import com.mob.bbssdk.gui.other.ums.datatype.Country;
import com.mob.bbssdk.gui.other.ums.datatype.EnumType;
import com.mob.bbssdk.gui.other.ums.datatype.Gender;
import com.mob.bbssdk.gui.other.ums.datatype.Province;
import com.mob.bbssdk.gui.other.ums.datatype.Zodiac;
import com.mob.bbssdk.gui.other.ums.pickers.Choice;
import com.mob.bbssdk.gui.other.ums.pickers.SingleValuePicker;
import com.mob.tools.utils.ResHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class SingleChoiceView extends LinearLayout {
	private TextView tvTitle;
	private boolean showTitle;
	private TextView tvChoice;
	private ArrayList<Choice> choices;
	private int[] selections;
	private String separator;
	private ImageView ivNext;

	public SingleChoiceView(final Page<?> page) {
		super(page.getContext());
		separator = ", ";
		init(getContext());
		setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SingleValuePicker.Builder builder = new SingleValuePicker.Builder(page.getContext(), page.getTheme());
				if (showTitle) {
					builder.showTitle();
				}
				builder.setChoices(choices);
				builder.setSelections(selections);
				builder.setOnSelectedListener(new SingleValuePicker.OnSelectedListener() {
					public void onSelected(int[] selections) {
						setSelections(selections);
					}
				});
				builder.show();
			}
		});
	}

	public void show() {
		performClick();
	}

	public SingleChoiceView(final Context context) {
		super(context);
		separator = ", ";
		init(getContext());
		setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SingleValuePicker.Builder builder = new SingleValuePicker.Builder(context, new DefaultTheme());
				if (showTitle) {
					builder.showTitle();
				}
				builder.setChoices(choices);
				builder.setSelections(selections);
				builder.setOnSelectedListener(new SingleValuePicker.OnSelectedListener() {
					public void onSelected(int[] selections) {
						setSelections(selections);
					}
				});
				builder.show();
			}
		});
	}


	private void init(Context context) {
		setOrientation(VERTICAL);

		LinearLayout ll = new LinearLayout(context);
		int dp53 = ResHelper.dipToPx(context, 44);
		int dp15 = ResHelper.dipToPx(context, 15);
		ll.setPadding(0, 0, dp15, 0);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, dp53);
		addView(ll, lp);

		tvTitle = new TextView(context);
		tvTitle.setMinEms(4);
		tvTitle.setTextColor(0xff3b3947);
		tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
		lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.leftMargin = dp15;
		lp.gravity = Gravity.CENTER_VERTICAL;
		ll.addView(tvTitle, lp);

		tvChoice = new TextView(context);
		tvChoice.setTextColor(0xff969696);
		tvChoice.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
		tvChoice.setText(ResHelper.getStringRes(context, "bbs_umssdk_default_unedit"));
		tvChoice.setGravity(Gravity.RIGHT);
		lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.CENTER_VERTICAL;
		lp.weight = 1;
		ll.addView(tvChoice, lp);

		ivNext = new ImageView(context);
		ivNext.setImageResource(ResHelper.getBitmapRes(context, "bbssdk_umssdk_default_go_details"));
		lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.CENTER_VERTICAL;
		lp.leftMargin = ResHelper.dipToPx(context, 5);
		ll.addView(ivNext, lp);
		ivNext.setVisibility(GONE);

		View vSep = new View(context);
		int resid = ResHelper.getBitmapRes(context, "bbssdk_umssdk_defalut_list_sep");
		vSep.setBackgroundDrawable(context.getResources().getDrawable(resid));
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
		addView(vSep, lp);
	}

	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	public void showTitle() {
		showTitle = true;
	}

	public synchronized void setChoices(ArrayList<Choice> choices) {
		this.choices = choices;
	}

	public ArrayList<Choice> getChoices() {
		return choices;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public void setSelections(int[] selections) {
		this.selections = selections;
		ArrayList<Choice> list = choices;
		StringBuilder sb = new StringBuilder();
		if (list != null) {
			for (int i = 0; i < selections.length; i++) {
				if (list.size() > selections[i]) {
					if (sb.length() > 0) {
						sb.append(separator);
					}
					sb.append(list.get(selections[i]).text);
					list = list.get(selections[i]).children;
				} else {
					break;
				}
			}
		}

		if (sb.length() > 0) {
			tvChoice.setTextColor(0xff3b3947);
			tvChoice.setText(sb.toString());
		} else {
			tvChoice.setTextColor(0xff969696);
			tvChoice.setText(ResHelper.getStringRes(tvChoice.getContext(), "umssdk_default_unedit"));
		}
		onSelectionsChange();
	}

	public Choice[] getSelections() {
		if (selections == null) {
			return null;
		}

		Choice[] cc = new Choice[selections.length];
		ArrayList<Choice> list = choices;
		for (int i = 0; i < selections.length; i++) {
			if (list.size() > selections[i]) {
				cc[i] = list.get(selections[i]);
				list = list.get(selections[i]).children;
			} else {
				break;
			}
		}

		return cc;
	}

	public static <T extends EnumType> ArrayList<Choice> createChoice(Class<T> type) {
		ArrayList<Choice> list = new ArrayList<Choice>();
		if (type.equals(Country.class)) {
			Country[] moveTotop = {
					Area.China.INSTANCE, Area.USA.INSTANCE, Area.Japan.INSTANCE,
					Area.Russia.INSTANCE, Area.UnitedKingdom.INSTANCE, Area.France.INSTANCE,
					Area.Germany.INSTANCE, Area.India.INSTANCE, Area.Australia.INSTANCE };
			Country[] countries = Area.values();
			Country[] movedCountries = new Country[countries.length];
			System.arraycopy(moveTotop, 0, movedCountries, 0, moveTotop.length);
			int j = moveTotop.length;
			for (int i = 0; i < countries.length; i++) {
				boolean found = false;
				for (Country c : moveTotop) {
					if (countries[i].equals(c)) {
						found = true;
						break;
					}
				}
				if (!found) {
					movedCountries[j] = countries[i];
					j++;
				}
			}
			for (Country country : movedCountries) {
				Choice c = new Choice();
				c.ext = country;
				int resid = ResHelper.getStringRes(MobSDK.getContext(), country.resName());
				c.text = MobSDK.getContext().getString(resid);
				list.add(c);
				Province[] provinces = null;
				try {
					Method mth = country.getClass().getMethod("values");
					provinces = (Province[]) mth.invoke(null);
				} catch (Throwable t) {}
				if (provinces != null) {
					for (Province provice : provinces) {
						Choice cp = new Choice();
						cp.ext = provice;
						resid = ResHelper.getStringRes(MobSDK.getContext(), provice.resName());
						cp.text = MobSDK.getContext().getString(resid);
						c.children.add(cp);
						City[] cities = null;
						try {
							Method mth = provice.getClass().getMethod("values");
							cities = (City[]) mth.invoke(null);
						} catch (Throwable t) {}
						if (cities != null) {
							for (City city : cities) {
								Choice cc = new Choice();
								cc.ext = city;
								resid = ResHelper.getStringRes(MobSDK.getContext(), city.resName());
								cc.text = MobSDK.getContext().getString(resid);
								cp.children.add(cc);
							}
						}
					}
				}
			}
		} else if (type.equals(Gender.class)) {
			for (Gender gender : Gender.values()) {
				Choice c = new Choice();
				c.ext = gender;
				int resid = ResHelper.getStringRes(MobSDK.getContext(), gender.resName());
				c.text = MobSDK.getContext().getString(resid);
				list.add(c);
			}
		} else if (type.equals(Zodiac.class)) {
			for (Zodiac zodiac : Zodiac.values()) {
				Choice c = new Choice();
				c.ext = zodiac;
				int resid = ResHelper.getStringRes(MobSDK.getContext(), zodiac.resName());
				c.text = MobSDK.getContext().getString(resid);
				list.add(c);
			}
		} else if (type.equals(Constellation.class)) {
			for (Constellation constellation : Constellation.values()) {
				Choice c = new Choice();
				c.ext = constellation;
				int resid = ResHelper.getStringRes(MobSDK.getContext(), constellation.resName());
				c.text = MobSDK.getContext().getString(resid);
				list.add(c);
			}
		}
		return list;
	}

	public void showNext() {
		ivNext.setVisibility(VISIBLE);
	}

	protected void onSelectionsChange() {

	}
	public static class DefaultTheme extends Theme {

		protected void initPageAdapters(Set<Class<? extends PageAdapter<?>>> adapters) {
			adapters.add(PhotoCropPageAdapter.class);
		}

		protected void initDialogAdapters(Set<Class<? extends DialogAdapter<?>>> adapters) {
			adapters.add(SingleValuePickerAdapter.class);
		}

		public int getDialogStyle(Context context) {
			return ResHelper.getStyleRes(context, "umssdk_default_DialogStyle");
		}

	}
}
