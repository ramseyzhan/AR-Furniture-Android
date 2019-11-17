package com.bongjlee.arfurnitureapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bongjlee.arfurnitureapp.R;
import com.bongjlee.arfurnitureapp.data.Product;

import java.util.ArrayList;


public class productAdapter extends ArrayAdapter<Product> {
    public productAdapter(Context context, ArrayList<Product> users) {
        super(context, 0, users);
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
        nameViewData.setText(prod_t.name);
        DescriptionViewData.setText(prod_t.description);
        productLinkViewData.setText(prod_t.shippingInfo);

        ImageView photoViewData;

        return convertView;
    }
}
