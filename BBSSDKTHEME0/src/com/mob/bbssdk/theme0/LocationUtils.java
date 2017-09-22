package com.mob.bbssdk.theme0;


import android.content.Context;

import com.mob.bbssdk.gui.helper.LocationHelper;
import com.mob.bbssdk.gui.other.ums.pickers.Choice;

import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;

public class LocationUtils {
	public static ArrayList<Choice> createChoice(Context context) {
		LocationHelper lh = new LocationHelper(context);
		List<LocationHelper.Province> listp = lh.getListProvinces();
		ArrayList<Choice> listchoices = new ArrayList<Choice>();
		for (LocationHelper.Province province : listp) {
			Choice prochoice = new Choice();
			prochoice.text = province.record.get("name");
			prochoice.ext = province.record;
			listchoices.add(prochoice);
			for (LocationHelper.City city : province.list) {
				Choice citychoice = new Choice();
				citychoice.text = city.record.get("name");
				citychoice.ext = city.record;
				prochoice.children.add(citychoice);
				for (CSVRecord record : city.list) {
					Choice recordchoice = new Choice();
					recordchoice.text = record.get("name");
					recordchoice.ext = record;
					citychoice.children.add(recordchoice);
				}
			}
		}
		return listchoices;
	}
}
