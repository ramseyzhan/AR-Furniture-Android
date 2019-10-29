package com.bongjlee.arfurnitureapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String value = intent.getStringExtra("toProductSub");
    }
    public void sendInfo(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> newProduct = new HashMap<>();
        EditText text = (EditText)findViewById(R.id.product_name);
        newProduct.put("ProductName", text.getText().toString());
        Map<String, Object> newProductDetails = new HashMap<>();
        text = (EditText)findViewById(R.id.product_description);
        newProductDetails.put("ProductDescription", text.getText().toString());
        text = (EditText)findViewById(R.id.product_price);
        newProductDetails.put("ProductPrice", text.getText().toString());
        text = (EditText)findViewById(R.id.product_styles);
        newProductDetails.put("ProductStyles", text.getText().toString());
        text = (EditText)findViewById(R.id.shipping_info);
        newProductDetails.put("ShippingInfo", text.getText().toString());
        newProduct.put("productInfo", newProductDetails);
        db.collection("products")
                .add(newProduct)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }
}
