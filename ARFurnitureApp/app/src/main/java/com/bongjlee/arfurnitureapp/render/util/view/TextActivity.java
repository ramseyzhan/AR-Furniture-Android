package com.bongjlee.arfurnitureapp.render.util.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TextActivity extends Activity {

	private TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle b = getIntent().getExtras();
		String title = b.getString("title");
		setTitle(title);
		
		String value = b.getString("text");

	}
}
