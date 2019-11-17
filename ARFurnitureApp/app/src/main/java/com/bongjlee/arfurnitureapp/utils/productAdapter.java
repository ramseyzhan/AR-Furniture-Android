package com.bongjlee.arfurnitureapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import com.bongjlee.arfurnitureapp.R;
import com.bongjlee.arfurnitureapp.data.Product;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.net.Uri;

import java.util.ArrayList;
import java.io.File;


public class productAdapter extends ArrayAdapter<Product> {
    private StorageReference storageReference;
    public productAdapter(Context context, ArrayList<Product> users,StorageReference store_ref) {
        super(context, 0, users);
        storageReference = store_ref;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product prod_t = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);
        }
        TextView nameViewData = (TextView) convertView.findViewById(R.id.product_name);
        TextView DescriptionViewData = (TextView) convertView.findViewById(R.id.product_price);
        TextView productLinkViewData = (TextView) convertView.findViewById(R.id.product_link);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.product_photo);
        imageView.setImageURI(Uri.fromFile(new File("/sdcard/cats.jpg")));


        nameViewData.setText(prod_t.name);
        DescriptionViewData.setText(prod_t.description);
        productLinkViewData.setText(prod_t.shippingInfo);

        ImageView photoViewData;

        return convertView;
    }
}
