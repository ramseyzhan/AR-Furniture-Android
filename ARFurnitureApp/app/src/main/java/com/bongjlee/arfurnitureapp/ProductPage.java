package com.bongjlee.arfurnitureapp;


import android.content.Intent;
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


public class ProductPage extends AppCompatActivity {
    private static final String TAG = HomePage.class.getSimpleName();
    private TextView nameViewData;
    private TextView DescriptionViewData;
    private TextView styleViewData;
    private TextView shippingInfoViewData;
    private TextView productLinkViewData;
    private TextView priceViewData;
    private ImageView photoViewData;


    private String name_t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
//        String value = intent.getStringExtra("productPage");
        String docId_t = getIntent().getStringExtra("name");



        nameViewData = findViewById(R.id.product_name);
        DescriptionViewData = findViewById(R.id.product_description);
        styleViewData = findViewById(R.id.product_styles);
        shippingInfoViewData = findViewById(R.id.shipping_info);
        //productLinkViewData = findViewById(R.id.product_link);
        priceViewData = findViewById(R.id.product_price);
        photoViewData = findViewById(R.id.product_photo);
        productLinkViewData = findViewById(R.id.product_link);



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("products").document(docId_t);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String id = documentSnapshot.getString("photoID");
                            String productDescription = documentSnapshot.getString("productDescription");
                            String name = documentSnapshot.getString("productName");
                            String style = documentSnapshot.getString("productStyles");
                            String shippinginfo = documentSnapshot.getString("shippingInfo");
                            String price = documentSnapshot.getString("ProductPrice");
                            name_t = name;
                            nameViewData.setText("Name: " + name);
                            priceViewData.setText("Price: " + price);
                            //productLinkViewData.setText("Product Page: ");
                            //photoViewData.set
                            DescriptionViewData.setText("Description: " + productDescription);
                            styleViewData.setText("Style: " + style);
                            shippingInfoViewData.setText("Connection information: " + shippinginfo);

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
        if(name_t.equals("sofa")) {
            Cartprods.name1 = "sofa";
            Cartprods.name2 = "chair";
        }
        else if(name_t.equals("chair")){
            Cartprods.name1 = "chair";
            Cartprods.name2 = "sofa";
        }
        else{
            Cartprods.name1 = "chair";
            Cartprods.name2 = "sofa";
            Cartprods.name3 = "chair";
            Cartprods.name4 = "sofa";
        }
        startActivity(intent);
    }
    public void back (View view) {
        this.finish();
    }
}
