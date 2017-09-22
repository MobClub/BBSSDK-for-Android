package com.mob.bbssdk.gui.helper;


import android.content.Context;

import com.mob.tools.utils.ResHelper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class LocationHelper {
	private List<Province> listProvinces = new ArrayList<Province>();

	public List<Province> getListProvinces() {
		return listProvinces;
	}

	public LocationHelper(Context context) {
		Reader in = null;
		InputStream inputstream = null;
		try {
			inputstream = context.getResources().openRawResource(ResHelper.getRawRes(context, "districtfull"));
			in = new InputStreamReader(inputstream);

			Iterable<CSVRecord> records = null;
			try {
				records = CSVFormat.TDF.withHeader("id", "name", "parent_id", "initial", "initials",
						"pinyin", "extra", "suffix", "code", "area_code", "order").parse(in);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

			List<City> citylistall = new ArrayList<City>();
			String strmark = context.getResources().getString(ResHelper.getStringRes(context, "bbs_location_municipality"));
			for (CSVRecord record : records) {
				if (record.get("parent_id").equals("0")) {
					Province province = new Province(record);
					listProvinces.add(new Province(record));
				} else {
					boolean iscity = false;
					for (Province province : listProvinces) {
						if (province.record.get("id").equals(record.get("parent_id"))) {
							//parent_id是0的直辖市
							if (province.record.get("suffix").equals(strmark)) {
								if (province.list.size() == 0) {
									City city = new City(province.record);
									province.list.add(city);
									citylistall.add(city);
								}
								iscity = false;
							} else {
								City city = new City(record);
								province.list.add(city);
								citylistall.add(city);
								iscity = true;
							}
						}
					}
					if (!iscity) {
						for (City city : citylistall) {
							if (city.record.get("id").equals(record.get("parent_id"))) {
								city.list.add(record);
							}
						}
					}
				}
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputstream != null) {
				try {
					inputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static class Province {
		public CSVRecord record;
		public List<City> list = new ArrayList<City>();

		public Province(CSVRecord record) {
			this.record = record;
		}
	}

	public static class City {
		public CSVRecord record;
		public List<CSVRecord> list = new ArrayList<CSVRecord>();

		public City(CSVRecord record) {
			this.record = record;
		}
	}
}
