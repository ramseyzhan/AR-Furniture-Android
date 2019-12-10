package com.bongjlee.arfurnitureapp.render.model3D.view;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bongjlee.arfurnitureapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends ListActivity {

	List<RowItem> rowItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);

		AssetManager assets = getApplicationContext().getAssets();
		String[] models = null;
		try {
			models = assets.list("models");
		} catch (IOException ex) {
			Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
			return;
		}

		// add 1 entry per model found
		rowItems = new ArrayList<RowItem>();
		for (String model : models) {
			if (model.endsWith(".obj")) {
				RowItem item = new RowItem("models/" + model, model, "models/" + model + ".jpg");
				rowItems.add(item);
			}
		}
		CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.activity_demo, rowItems);
		setListAdapter(adapter);
	}

	private void loadDemo(final String selectedItem) {
		Intent intent = new Intent(DemoActivity.this.getApplicationContext(), ModelActivity.class);
		Bundle b = new Bundle();
		b.putString("assetDir", "models/");
		b.putString("assetFilename", selectedItem);
		intent.putExtras(b);
		DemoActivity.this.startActivity(intent);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		final RowItem selectedItem = (RowItem) getListView().getItemAtPosition(position);
		loadDemo(selectedItem.name);


	}
}

class RowItem {

	String image;

	String name;

	String path;

	public RowItem(String path, String name, String image) {
		this.path = path;
		this.name = name;
		this.image = image;
	}
}

class CustomListViewAdapter extends ArrayAdapter<RowItem> {

	Context context;

	public CustomListViewAdapter(Context context, int resourceId, List<RowItem> items) {
		super(context, resourceId, items);
		this.context = context;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView imageView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		RowItem rowItem = getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.activity_demo_item, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.demo_item_icon);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		try {
			Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open(rowItem.image));
			holder.imageView.setImageBitmap(bitmap);
		} catch (Exception e) {
		}

		return convertView;
	}
}