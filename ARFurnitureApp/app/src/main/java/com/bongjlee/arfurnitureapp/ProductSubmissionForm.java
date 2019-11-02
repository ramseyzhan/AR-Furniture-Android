package com.bongjlee.arfurnitureapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bongjlee.arfurnitureapp.data.Product;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;



public class ProductSubmissionForm extends AppCompatActivity {
    private static final String TAG = ProductSubmissionForm.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_submission);
//        Intent intent = getIntent();
//        String value = intent.getStringExtra("toProductSub");
    }
    public void sendInfo(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Product product             = new Product();
        EditText productName        = findViewById(R.id.product_name);
        EditText productDescription = findViewById(R.id.product_description);
        EditText productPrice       = findViewById(R.id.product_price);
        EditText productStyles      = findViewById(R.id.product_styles);
        EditText shippingInfo       = findViewById(R.id.shipping_info);

        product.setProductName(productName.getText().toString());
        product.setProductDescription(productDescription.getText().toString());
        product.setProductPrice(productPrice.getText().toString());
        product.setProductStyles(productStyles.getText().toString());
        product.setShippingInfo(shippingInfo.getText().toString());

        //Submit data into FireBase cloud
        db.collection("products")
                .add(product)
                .addOnSuccessListener(
                        documentReference ->
                                Log.d(
                                        TAG,
                                        "DocumentSnapshot added with ID: " + documentReference.getId()
                                )
                )
                .addOnFailureListener(
                        e -> Log.w(TAG, "Error adding document", e)
                );

    }
}
