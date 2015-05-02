package com.example.database_android_myattempt;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewDatabase extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewdatabase);
		SQLiteHelper showdatabase = new SQLiteHelper(this);
		String database_returned = showdatabase.getData();
		TextView tvViewDataabase = (TextView) findViewById(R.id.tvViewDatabase);
		tvViewDataabase.setText(database_returned);
	}

}
