package com.bongjlee.arfurnitureapp.utils;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bongjlee.arfurnitureapp.R;
import com.bongjlee.arfurnitureapp.data.Reviews;
import com.bongjlee.arfurnitureapp.data.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class reviewAdapter extends ArrayAdapter<Reviews> {
    private StorageReference storageReference;
    public reviewAdapter(Context context, ArrayList<Reviews> users, StorageReference store_ref) {
        super(context, 0, users);
        storageReference = store_ref;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Reviews review_t = getItem(position);
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
