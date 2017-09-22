package com.mob.bbssdk.theme0.page.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mob.bbssdk.API;
import com.mob.bbssdk.APICallback;
import com.mob.bbssdk.BBSSDK;
import com.mob.bbssdk.api.UserAPI;
import com.mob.bbssdk.gui.BBSViewBuilder;
import com.mob.bbssdk.gui.helper.DataConverterHelper;
import com.mob.bbssdk.gui.helper.ErrorCodeHelper;
import com.mob.bbssdk.gui.other.ums.SingleChoiceView;
import com.mob.bbssdk.gui.other.ums.datatype.Gender;
import com.mob.bbssdk.gui.other.ums.pickers.Choice;
import com.mob.bbssdk.gui.pages.profile.PageEditSignature;
import com.mob.bbssdk.gui.pages.profile.PageUserProfileDetails;
import com.mob.bbssdk.gui.utils.ToastUtils;
import com.mob.bbssdk.model.User;
import com.mob.bbssdk.theme0.LocationUtils;
import com.mob.bbssdk.theme0.page.Theme0StyleModifier;
import com.mob.bbssdk.utils.StringUtils;
import com.mob.tools.utils.ResHelper;

import org.apache.commons.csv.CSVRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Theme0PageUserProfileDetails extends PageUserProfileDetails {

	SingleChoiceView scvBirthday;
	SingleChoiceView scvLocation;
	SingleChoiceView scvGender;

	@Override
	protected View onCreateContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(getLayoutId("bbs_theme0_userprofiledetails"), null);
		return view;
	}

	@Override
	protected void onViewCreated(View contentView) {
		super.onViewCreated(contentView);
		titleBar.setLeftImageResource(getDrawableId("bbs_titlebar_back_black"));
		titleBar.setTitle(getStringRes("theme0_pageuserprofiledetails_title"));
		Theme0StyleModifier.modifyUniformWhiteStyle(this);

		layoutSignature.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (userInfo != null) {
					PageEditSignature page = BBSViewBuilder.getInstance().buildPageEditSignature();
					page.initPage(userInfo.sightml);
					page.show(getContext());
				}
			}
		});

		layoutGender.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (scvGender == null) {
					scvGender = new SingleChoiceView(getContext()) {
						@Override
						protected void onSelectionsChange() {
							super.onSelectionsChange();
							Choice[] choices = getSelections();
							if (choices.length == 1) {
								Gender gender = (Gender) choices[0].ext;
								if (gender.code() != userInfo.gender) {
									updateGener(gender.code());
								}
							}
						}
					};
					scvGender.setChoices(SingleChoiceView.createChoice(Gender.class));
				}
				ArrayList<Choice> choices = scvGender.getChoices();
				int[] selections = new int[1];
				boolean founded = false;
				for (int i = 0; i < choices.size(); i++) {
					Gender gender = (Gender) choices.get(i).ext;
					if (gender.code() == userInfo.gender) {
						selections[0] = i;
						founded = true;
					}
				}
				if (founded) {
					scvGender.setSelections(selections);
				}
				scvGender.show();
			}
		});

		layoutLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (scvLocation == null) {
					scvLocation = new SingleChoiceView(getContext()) {
						@Override
						protected void onSelectionsChange() {
							super.onSelectionsChange();
							Choice[] choice = getSelections();
							if (choice != null && choice.length == 3) {
								CSVRecord province = choice[0] == null ? null : (CSVRecord) choice[0].ext;
								CSVRecord city = choice[1] == null ? null : (CSVRecord) choice[1].ext;
								CSVRecord district = choice[2] == null ? null : (CSVRecord) choice[2].ext;
								String strprovince = getText(choice[0]);
								String strcity = getText(choice[1]);
								String strdistrict = getText(choice[2]);

								String result = DataConverterHelper.buildShortLocatinText(strprovince, strcity, strdistrict);
								if (!result.equals(DataConverterHelper.getShortLoationText(userInfo))) {
									updateLocation(result);
								}
							}
						}

						public String getText(Choice choice) {
							if (choice == null) {
								return "";
							} else {
								if (StringUtils.isEmpty(choice.text)) {
									return "";
								} else {
									return choice.text;
								}
							}
						}
					};
					scvLocation.setChoices(LocationUtils.createChoice(getContext()));
				}
				//try to find the matching selection in the choices.
				ArrayList<Choice> choices = scvLocation.getChoices();
				boolean founded = false;
				int[] selections = new int[3];
				boolean endloop = false;
				for (int i = 0; i < choices.size(); i++) {
					if (endloop) {
						break;
					}
					if (choices.get(i).text.equals(userInfo.resideprovince)) {
						selections[0] = i;
						choices = choices.get(i).children;
						for (int j = 0; j < choices.size(); j++) {
							if (endloop) {
								break;
							}
							if (choices.get(j).text.equals(userInfo.residecity)) {
								selections[1] = j;
								choices = choices.get(j).children;
								for (int k = 0; k < choices.size(); k++) {
									if (choices.get(k).text.equals(userInfo.residedist)) {
										selections[2] = k;
										founded = true;
										endloop = true;
										break;
									}
								}
							}
						}
					}
				}
				if (founded) {
					scvLocation.setSelections(selections);
				}
				scvLocation.show();
			}
		});
		layoutBirthday.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (scvBirthday == null) {
					scvBirthday = new SingleChoiceView(getContext()) {
						protected void onSelectionsChange() {
							Choice[] choice = getSelections();
							if (choice != null && choice.length == 3) {
								Calendar cal = Calendar.getInstance();
								cal.set(Calendar.YEAR, (Integer) choice[0].ext);
								cal.set(Calendar.MONTH, ((Integer) choice[1].ext) - 1);
								cal.set(Calendar.DAY_OF_MONTH, (Integer) choice[2].ext);
								String sel = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
								String birth = DataConverterHelper.getBirthday(userInfo);
								String bid = null;
								if (cal.getTimeInMillis() > System.currentTimeMillis()) {
									ToastUtils.showToast(getContext(), getStringRes("theme0_setbirthday_error"));
								} else {
									//update the birthday unless the data set by the user don't equal to the data got from server.
									String result = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()).toString();
									String birthyear = String.format("%4d", userInfo.birthyear);
									String birthmoth = String.format("%02d", userInfo.birthmonth);
									String birthday = String.format("%02d", userInfo.birthday);
									String userbirth = birthyear + "-" + birthmoth + "-" + birthday;
									if (!result.equals(userbirth)) {
										updateBirthday(result);
									}
								}

							}
						}
					};
					scvBirthday.setChoices(getDateChoices());
				}

				ArrayList<Choice> choices = scvBirthday.getChoices();
				boolean founded = false;
				int[] selections = new int[3];
				String birthyear = String.format("%4d", userInfo.birthyear);
				String birthmoth = String.format("%02d", userInfo.birthmonth);
				String birthday = String.format("%02d", userInfo.birthday);
				boolean endloop = false;
				for (int i = 0; i < choices.size(); i++) {
					if (endloop) {
						break;
					}
					if (choices.get(i).text.startsWith(String.valueOf(userInfo.birthyear))) {
						selections[0] = i;
						choices = choices.get(i).children;
						for (int j = 0; j < choices.size(); j++) {
							if (endloop) {
								break;
							}
							if (choices.get(j).text.startsWith(String.valueOf(birthmoth))) {
								selections[1] = j;
								choices = choices.get(j).children;
								for (int k = 0; k < choices.size(); k++) {
									if (choices.get(k).text.startsWith(String.valueOf(birthday))) {
										selections[2] = k;
										founded = true;
										endloop = true;
										break;
									}
								}
							}
						}
					}
				}
				if (founded) {
					scvBirthday.setSelections(selections);
				}
				scvBirthday.show();
			}
		});
	}

	protected void updateBirthday(String birth) {
		BBSSDK.getApi(UserAPI.class).updateUserInfo(null, null, null, birth, null, false, new APICallback<User>() {
			@Override
			public void onSuccess(API api, int action, User result) {
				ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "theme0_edituserprofile_success"));
				updateInfoFromServer();
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(getContext(), errorCode, details);
			}
		});
	}

	protected void updateGener(int gender) {
		BBSSDK.getApi(UserAPI.class).updateUserInfo(gender, null, null, null, null, false, new APICallback<User>() {
			@Override
			public void onSuccess(API api, int action, User result) {
				ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "theme0_edituserprofile_success"));
				updateInfoFromServer();
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(getContext(), errorCode, details);
			}
		});
	}

	protected void updateLocation(String location) {
		BBSSDK.getApi(UserAPI.class).updateUserInfo(null, null, null, null, location, false, new APICallback<User>() {
			@Override
			public void onSuccess(API api, int action, User result) {
				ToastUtils.showToast(getContext(), ResHelper.getStringRes(getContext(), "theme0_edituserprofile_success"));
				updateInfoFromServer();
			}

			@Override
			public void onError(API api, int action, int errorCode, Throwable details) {
				ErrorCodeHelper.toastError(getContext(), errorCode, details);
			}
		});
	}

	@Override
	protected void OnInfoUpdated() {
		super.OnInfoUpdated();
	}

	private ArrayList<Choice> getDateChoices() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		int thisYear = cal.get(Calendar.YEAR);
		ArrayList<Choice> years = new ArrayList<Choice>();
		int resid = ResHelper.getStringRes(getContext(), "umssdk_default_year");
		String strYear = getContext().getString(resid);
		resid = ResHelper.getStringRes(getContext(), "umssdk_default_month");
		String strMonth = getContext().getString(resid);
		resid = ResHelper.getStringRes(getContext(), "umssdk_default_day");
		String strDay = getContext().getString(resid);
		int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

		for (int year = thisYear - 100; year <= thisYear; year++) {
			Choice cYear = new Choice();
			cYear.ext = year;
			cYear.text = year + strYear;
			years.add(cYear);
			daysOfMonth[1] = ResHelper.isLeapYear(year) ? 29 : 28;
			for (int month = 1; month <= 12; month++) {
				Choice cMonth = new Choice();
				cMonth.ext = month;
				if (month < 10) {
					cMonth.text = "0" + month + strMonth;
				} else {
					cMonth.text = month + strMonth;
				}
				cYear.children.add(cMonth);
				for (int day = 1; day <= daysOfMonth[month - 1]; day++) {
					Choice cDay = new Choice();
					cDay.ext = day;
					if (day < 10) {
						cDay.text = "0" + day + strDay;
					} else {
						cDay.text = day + strDay;
					}
					cMonth.children.add(cDay);
				}
			}
		}
		return years;
	}
}
