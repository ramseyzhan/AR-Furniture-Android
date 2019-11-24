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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.IOException;
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
        TextView productIDViewData = (TextView) convertView.findViewById(R.id.product_id);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.product_photo);

        nameViewData.setText(prod_t.getName());
        DescriptionViewData.setText(prod_t.getDescription());
        productLinkViewData.setText(prod_t.getShippingInfo());
        productIDViewData.setText(prod_t.getId());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference spaceRef = storageRef.child("images/"+prod_t.getPhotoId()+".jpg");
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
