package com.mob.bbssdk.theme0;


import android.content.Context;

import com.mob.bbssdk.gui.helper.LocationDBHelper;
import com.mob.bbssdk.gui.helper.LocationHelper;
import com.mob.bbssdk.gui.other.ums.pickers.Choice;

import java.util.ArrayList;
import java.util.List;

public class LocationUtils {
	public static ArrayList<Choice> createChoice(Context context) {
		LocationHelper lh = new LocationHelper(context);
		List<LocationDBHelper.Item> listp = lh.getListProvinces();
		ArrayList<Choice> listchoices = new ArrayList<Choice>();
		for (LocationDBHelper.Item province : listp) {
			Choice prochoice = new Choice();
			prochoice.text = province.name;
			prochoice.ext = province;
			listchoices.add(prochoice);
			for (LocationDBHelper.Item city : province.list) {
				Choice citychoice = new Choice();
				citychoice.text = city.name;
				citychoice.ext = city;
				prochoice.children.add(citychoice);
				for (LocationDBHelper.Item district : city.list) {
					Choice recordchoice = new Choice();
					recordchoice.text = district.name;
					recordchoice.ext = district;
					citychoice.children.add(recordchoice);
				}
			}
		}
		return listchoices;
	}
}
