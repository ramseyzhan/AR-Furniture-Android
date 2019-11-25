package com.bongjlee.arfurnitureapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bongjlee.arfurnitureapp.R;
import com.bongjlee.arfurnitureapp.data.Review;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class reviewAdapter extends ArrayAdapter<Review> {
    private StorageReference storageReference;
    public reviewAdapter(Context context, ArrayList<Review> users, StorageReference store_ref) {
        super(context, 0, users);
        storageReference = store_ref;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Review review_t = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);
        }
        TextView reviewerViewData = (TextView) convertView.findViewById(R.id.reviewer_name);
        TextView contentViewData = (TextView) convertView.findViewById(R.id.review_content);
        TextView ratingViewData = (TextView) convertView.findViewById(R.id.review_rating);

        reviewerViewData.setText(review_t.getUserID());
        contentViewData.setText(review_t.getReview_content());
        ratingViewData.setText(review_t.getReview_rating());

        return convertView;
    }

}
