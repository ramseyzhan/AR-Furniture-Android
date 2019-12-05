package com.bongjlee.arfurnitureapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bongjlee.arfurnitureapp.R;
import com.bongjlee.arfurnitureapp.data.Review;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class reviewAdapter extends ArrayAdapter<String> {
    private StorageReference storageReference;
    public reviewAdapter(Context context, ArrayList<String> users, StorageReference store_ref) {
        super(context, 0, users);
        storageReference = store_ref;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String review_id = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);
        }
        TextView reviewerViewData = (TextView) convertView.findViewById(R.id.reviewer_name);
        TextView contentViewData = (TextView) convertView.findViewById(R.id.review_content);
        TextView ratingViewData = (TextView) convertView.findViewById(R.id.review_rating);

        FirebaseFirestore db  = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("reviews").document(review_id);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            reviewerViewData.setText(documentSnapshot.getString("userID"));
                            contentViewData.setText(documentSnapshot.getString("review_content"));
                            ratingViewData.setText(documentSnapshot.getString("review_rating"));
                        }
                    }
                });



        return convertView;
    }

}
