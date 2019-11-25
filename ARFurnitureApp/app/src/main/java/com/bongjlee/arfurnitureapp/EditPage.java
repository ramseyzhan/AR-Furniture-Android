package com.bongjlee.arfurnitureapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




public class EditPage extends AppCompatActivity {
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
        setContentView(R.layout.activity_edit_product);

        Intent intent = getIntent();
        this.docId_t = getIntent().getStringExtra("p_id");

        nameViewData = findViewById(R.id.product_name);
        DescriptionViewData = findViewById(R.id.product_description);
        styleViewData = findViewById(R.id.product_styles);
        shippingInfoViewData = findViewById(R.id.shipping_info);
        priceViewData = findViewById(R.id.product_price);
        photoViewData = findViewById(R.id.product_photo);
    }
    public void edit (View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //get the new product info

        String productName = ( (EditText) findViewById( R.id.product_name_edit ) ).getText().toString();
        String productDescription = ( (EditText) findViewById( R.id.product_description_edit) ).getText().toString();
        String productPrice = ( (EditText) findViewById( R.id.product_price_edit ) ).getText().toString();
        String productStyles = ( (EditText) findViewById( R.id.product_styles_edit ) ).getText().toString();
        String shippingInfo = ( (EditText) findViewById( R.id.shipping_info_edit ) ).getText().toString();

        DocumentReference docRef = db.collection("products").document(this.docId_t);
        docRef.update("productName", productName);
        docRef.update("productDescription", productDescription);
        docRef.update("ProductPrice", productPrice);
        docRef.update("productStyles", productStyles);
        docRef.update("shippingInfo", shippingInfo);
        Intent intent = new Intent(EditPage.this, ProductPage.class);
        intent.putExtra("p_id", this.docId_t);
        startActivity(intent);
    }

    public void back (View view) {
        this.finish();
    }
}

