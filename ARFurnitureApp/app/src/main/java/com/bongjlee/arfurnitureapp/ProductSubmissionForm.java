package com.bongjlee.arfurnitureapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.bongjlee.arfurnitureapp.data.Product;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProductSubmissionForm extends AppCompatActivity {
    private static final String TAG = ProductSubmissionForm.class.getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_product_submission );
//        Intent intent = getIntent();
//        String value = intent.getStringExtra("toProductSub");
    }

    public void sendInfo( View view ) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Product product = new Product();
        String productName = ( (EditText) findViewById( R.id.product_name ) ).getText().toString();
        String productDescription = ( (EditText) findViewById( R.id.product_description ) ).getText().toString();
        String productPrice = ( (EditText) findViewById( R.id.product_price ) ).getText().toString();
        String productStyles = ( (EditText) findViewById( R.id.product_styles ) ).getText().toString();
        String shippingInfo = ( (EditText) findViewById( R.id.shipping_info ) ).getText().toString();

        product.setProductName( productName );
        product.setProductDescription( productDescription );
        product.setProductPrice( productPrice );
        product.setProductStyles( productStyles );
        product.setShippingInfo( shippingInfo );

        //Submit data into FireBase cloud
        db.collection( "products" )
                .document(
                        String.valueOf( productName.hashCode() )
                )
                .set( product )
                .addOnSuccessListener(
                        documentReference ->
                                Log.d(
                                        TAG,
                                        "DocumentSnapshot added with ID: " + documentReference.toString()
                                )
                )
                .addOnFailureListener(
                        e -> Log.w( TAG, "Error adding document", e )
                );

    }
}
