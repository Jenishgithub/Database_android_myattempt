package com.example.database_android_myattempt;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	EditText etname, etHotness;
	Button btnUpdatedatabase, btnViewDatabase;
	Button btnDeleteDatabase;
	RelativeLayout rlResetDatabase;
	ProgressBar pbWwait;
//yo
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etname = (EditText) findViewById(R.id.etName);
		etHotness = (EditText) findViewById(R.id.etHotness);
		btnUpdatedatabase = (Button) findViewById(R.id.btnUpdateDatabase);
		btnViewDatabase = (Button) findViewById(R.id.btnViewDatabase);
		btnDeleteDatabase = (Button) findViewById(R.id.btnDeleteDatabase);
		rlResetDatabase = (RelativeLayout) findViewById(R.id.rlResetDatabase);
		pbWwait = (ProgressBar) findViewById(R.id.pbWait);

		btnUpdatedatabase.setOnClickListener(this);
		btnViewDatabase.setOnClickListener(this);
		btnDeleteDatabase.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnUpdateDatabase:
			String name = etname.getText().toString();
			String hotness = etHotness.getText().toString();
			try {
				new SQLiteHelper(MainActivity.this).createEntry(name, hotness);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String error = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Error mother fucker");
				TextView tv = new TextView(this);
				tv.setText(error);
				d.setContentView(tv);
				d.show();

			}
			break;

		case R.id.btnViewDatabase:
			startActivity(new Intent(getApplicationContext(),
					ViewDatabase.class));
			break;

		case R.id.btnDeleteDatabase:

			AsyncTaskRunner runner = new AsyncTaskRunner();
			runner.execute();

			break;
		default:
			break;
		}
	}

	class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pbWwait.setVisibility(View.VISIBLE);
			rlResetDatabase.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			List<String> tables = new ArrayList<>();
			SQLiteHelper mahHelper = new SQLiteHelper(MainActivity.this);
			SQLiteDatabase db = mahHelper.getReadableDatabase();
			String query_select_tables = "select * from sqlite_master where type='table';";
			Cursor cur_select_tables = db.rawQuery(query_select_tables, null);
			cur_select_tables.moveToFirst();
			while (!cur_select_tables.isAfterLast()) {
				String tableName = cur_select_tables.getString(1);
				if ((!tableName.equals("android_metadata"))
						&& (!tableName.equals("sqlite_sequence"))) {
					tables.add(tableName);

				}
				cur_select_tables.moveToNext();
			}
			cur_select_tables.close();
			Log.d("Jenish Shkaya", "the list of tables in this database: "
					+ tables);

			// now im going delete all the table in this database....

			for (String eachTable : tables) {
				db.execSQL("drop table if exists " + eachTable);

			}

			List<String> tables_afterdeletion = new ArrayList<>();
			String query_select = "select * from sqlite_master where type='table';";
			cur_select_tables = db.rawQuery(query_select, null);
			cur_select_tables.moveToFirst();
			while (!cur_select_tables.isAfterLast()) {
				String tableName = cur_select_tables.getString(1);
				if ((!tableName.equals("android_metadata"))
						&& (!tableName.equals("sqlite_sequence")))
					tables_afterdeletion.add(tableName);
				cur_select_tables.moveToNext();
			}
			cur_select_tables.close();
			Log.d("jenish shakya", "the list of tables after deletion; "
					+ tables_afterdeletion);
			// now create table again
			new SQLiteHelper(MainActivity.this).onCreate(db);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pbWwait.setVisibility(View.GONE);
			rlResetDatabase.setVisibility(View.VISIBLE);
		}

	}
}
