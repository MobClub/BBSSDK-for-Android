package com.mob.bbssdk.gui.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pan on 14/11/2017.
 */

public class LocationDBHelper extends SQLiteOpenHelper {
	private static final String TAG = "LocationDBHelper";

	private Context context;

	public LocationDBHelper(Context context, String name) {
		super(context, name, null, 1);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

	}

	public Cursor getAllData() {
		SQLiteDatabase db = getReadableDatabase();
		Cursor res = db.rawQuery("SELECT id, name FROM district", null);
		return res;
	}

	public List<Item> getAllProvince() {
		SQLiteDatabase db = getReadableDatabase();
		String strmark = context.getResources().getString(ResHelper.getStringRes(context, "bbs_location_municipality"));

		Cursor cursor = db.rawQuery("SELECT id, name, parent_id, suffix FROM district WHERE parent_id = 0", null);
		List<Item> province = new ArrayList<Item>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Item item = parseItem(cursor);
			province.add(item);
			cursor.moveToNext();
		}
		cursor.close();

		for (Item itemprovince : province) {
			if (itemprovince.suffix.equals(strmark)) {
				Item city = new Item(itemprovince);
				Cursor cursordistrict = db.rawQuery("SELECT id, name, parent_id, suffix FROM district WHERE parent_id = " + city.id, null);
				cursordistrict.moveToFirst();
				while (!cursordistrict.isAfterLast()) {
					Item district = parseItem(cursordistrict);
					city.list.add(district);
					cursordistrict.moveToNext();
				}
				cursordistrict.close();
				itemprovince.list.add(city);
			} else {
				Cursor cursorcity = db.rawQuery("SELECT id, name, parent_id, suffix FROM district WHERE parent_id = " + itemprovince.id, null);
				cursorcity.moveToFirst();
				while (!cursorcity.isAfterLast()) {
					Item city = parseItem(cursorcity);
					Cursor cursordistrict = db.rawQuery("SELECT id, name, parent_id, suffix FROM district WHERE parent_id = " + city.id, null);
					cursordistrict.moveToFirst();
					while (!cursordistrict.isAfterLast()) {
						Item district = parseItem(cursordistrict);
						city.list.add(district);
						cursordistrict.moveToNext();
					}
					cursordistrict.close();
					cursorcity.moveToNext();
					itemprovince.list.add(city);
				}
				cursorcity.close();
			}
		}
		return province;
	}

	static Item parseItem(Cursor cursor) {
		Item item = new Item();
		item.id = cursor.getInt(0);
		item.name = cursor.getString(1);
		item.parentid = cursor.getInt(2);
		item.suffix = cursor.getString(3);
		item.list = new ArrayList<Item>();
		return item;
	}

	public static class Item {
		public int id;
		public String name;
		public int parentid;
		public String suffix;
		public List<Item> list = new ArrayList<Item>();

		public Item() {

		}

		public Item(Item item) {
			this.id = item.id;
			this.name = item.name;
			this.parentid = item.parentid;
			this.suffix = item.suffix;
			this.list.addAll(item.list);
		}
	}
}
