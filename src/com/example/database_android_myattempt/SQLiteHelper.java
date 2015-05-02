package com.example.database_android_myattempt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "HotOrNotdb";
	public static final String DATABASE_TABLE = "peopleTable";
	public static final int DATABASE_VERSION = 1;

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "person_name";
	public static final String KEY_HOTNESS = "person_hotness";
	public static String DB_PATH = null;

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		DB_PATH = context.getDatabasePath(DATABASE_NAME).getPath();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table " + DATABASE_TABLE + " (" + KEY_ROWID
				+ " integer primary key autoincrement, " + KEY_NAME
				+ " text not null, " + KEY_HOTNESS + " text not null);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("delete from " + DATABASE_NAME);
		onCreate(db);
	}

	public long createEntry(String name, String hotness) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, name);
		cv.put(KEY_HOTNESS, hotness);
		return db.insert(DATABASE_TABLE, null, cv);

	}

	public String getData() {
		String result = "";
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "select * from " + DATABASE_TABLE;
		Cursor cur = db.rawQuery(query, null);
		cur.moveToFirst();
		int col_id = cur.getColumnIndex(KEY_ROWID);
		int col_name = cur.getColumnIndex(KEY_NAME);
		int col_hotness = cur.getColumnIndex(KEY_HOTNESS);

		while (!cur.isAfterLast()) {
			// result = result + cur.getString(cur.getColumnIndex(KEY_ROWID))
			// + " " + cur.getString(cur.getColumnIndex(KEY_NAME)) + " "
			// + cur.getString(cur.getColumnIndex(KEY_HOTNESS)) + "\n";
			// cur.moveToNext();

			result = result + cur.getString(col_id) + " "
					+ cur.getString(col_name) + " "
					+ cur.getString(col_hotness) + "\n";
			cur.moveToNext();

		}

		return result;

	}
}