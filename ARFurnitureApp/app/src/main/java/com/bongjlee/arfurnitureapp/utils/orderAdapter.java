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
import com.bongjlee.arfurnitureapp.data.Order;
import com.bongjlee.arfurnitureapp.data.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class orderAdapter extends ArrayAdapter<Order> {
    private StorageReference storageReference;
    public orderAdapter(Context context, ArrayList<Order> users, StorageReference store_ref) {
        super(context, 0, users);
        storageReference = store_ref;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order_t = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_review_item, parent, false);
        }
        TextView nameViewData = (TextView) convertView.findViewById(R.id.product_name);
        TextView priceViewData = (TextView) convertView.findViewById(R.id.product_price);
        TextView storeViewData = (TextView) convertView.findViewById(R.id.product_store);
        TextView orderIDViewData = (TextView) convertView.findViewById(R.id.order_id);
        TextView orderDateViewData = (TextView) convertView.findViewById(R.id.order_date);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.product_photo);

        nameViewData.setText(order_t.getProduct_name());
        priceViewData.setText(order_t.getProduct_price());
        storeViewData.setText(order_t.getProduct_store());
        orderIDViewData.setText(order_t.getOrder_id());
        orderDateViewData.setText(order_t.getTimestamp());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference spaceRef = storageRef.child("images/"+order_t.getProduct_photo_id()+".jpg");
        try{
            File localFile = File.createTempFile("images", "jpg");
            spaceRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    imageView.setImageURI(Uri.fromFile(localFile));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
        catch (IOException e){
        }
        return convertView;
    }
}
