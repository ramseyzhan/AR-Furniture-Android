package com.bongjlee.arfurnitureapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.bongjlee.arfurnitureapp.data.Cartprods;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


public class ProductPage extends AppCompatActivity {
    private TextView nameViewData;
    private TextView DescriptionViewData;
    private TextView styleViewData;
    private TextView shippingInfoViewData;
    private TextView productLinkViewData;
    private TextView priceViewData;
    private ImageView photoViewData;

    private String docId_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        this.docId_t = getIntent().getStringExtra("p_id");

        nameViewData = findViewById(R.id.product_name);
        DescriptionViewData = findViewById(R.id.product_description);
        styleViewData = findViewById(R.id.product_styles);
        shippingInfoViewData = findViewById(R.id.shipping_info);
        //productLinkViewData = findViewById(R.id.product_link);
        priceViewData = findViewById(R.id.product_price);
        photoViewData = findViewById(R.id.product_photo);
//        productLinkViewData = findViewById(R.id.product_link);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("products").document(docId_t);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String photoId = documentSnapshot.getString("photoId");
                            String productDescription = documentSnapshot.getString("productDescription");
                            String name = documentSnapshot.getString("productName");
                            String style = documentSnapshot.getString("productStyles");
                            String shippinginfo = documentSnapshot.getString("shippingInfo");
                            String price = documentSnapshot.getString("ProductPrice");
                            nameViewData.setText("Name: " + name);
                            priceViewData.setText("Price: " + price);
                            DescriptionViewData.setText("Description: " + productDescription);
                            styleViewData.setText("Style: " + style);
                            shippingInfoViewData.setText("Connection information: " + shippinginfo);
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReference();

                            StorageReference spaceRef = storageRef.child("images/"+photoId+".jpg");
                            try{
                                File localFile = File.createTempFile("images", "jpg");
                                spaceRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        photoViewData.setImageURI(Uri.fromFile(localFile));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });
                            }
                            catch (IOException e){
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    public void GeneratAR(View view) {
        Intent intent = new Intent(this, ARViewPage.class);

        intent.putExtra("p_id", this.docId_t);
        startActivity(intent);
    }
    public void Purchase (View view){
        startActivity(new Intent(ProductPage.this, BuyingPage.class));
    }
    public void back (View view) {
        this.finish();
    }
}
