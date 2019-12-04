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
import com.bongjlee.arfurnitureapp.data.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class productAdapter extends ArrayAdapter<String> {
    private StorageReference storageReference;
    public productAdapter(Context context, ArrayList<String> users,StorageReference store_ref) {
        super(context, 0, users);
        storageReference = store_ref;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String prod_id = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);
        }
        TextView nameViewData = (TextView) convertView.findViewById(R.id.product_name);
        TextView DescriptionViewData = (TextView) convertView.findViewById(R.id.product_price);
        TextView productLinkViewData = (TextView) convertView.findViewById(R.id.product_link);
        TextView productIDViewData = (TextView) convertView.findViewById(R.id.product_id);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.product_photo);
        FirebaseFirestore db  = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("products").document(prod_id);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String photoId = documentSnapshot.getString("photoId");
                            productIDViewData.setText(prod_id);
                            DescriptionViewData.setText(documentSnapshot.getString("description"));
                            nameViewData.setText(documentSnapshot.getString("name"));
                            productLinkViewData.setText(documentSnapshot.getString("shippingInfo"));
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReference();
                            StorageReference spaceRef = storageRef.child("images/"+photoId+".jpg");
                            try{
                                File localFile = File.createTempFile("images", "jpg");
                                spaceRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        imageView.setImageURI(Uri.fromFile(localFile));
                                    }
                                });
                            }
                            catch (IOException e){
                            }
                        }
                    }
                });



        return convertView;
    }
}
