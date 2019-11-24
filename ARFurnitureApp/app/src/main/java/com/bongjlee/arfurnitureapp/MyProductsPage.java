package com.bongjlee.arfurnitureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bongjlee.arfurnitureapp.data.Product;
import com.bongjlee.arfurnitureapp.utils.productAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class MyProductsPage extends AppCompatActivity {

    private ArrayList<Product> prodArrayList;
    private productAdapter prodAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products_page);

        db = FirebaseFirestore.getInstance();
        prodArrayList = new ArrayList<Product>();
        prodAdapter = new productAdapter(this, prodArrayList,
                FirebaseStorage.getInstance().getReference());

        ListView lView = (ListView) findViewById(R.id.chattListView);
        lView.setAdapter(prodAdapter);
        prodAdapter.clear();

        for (int i = 0; i < 10; i++) {
            prodAdapter.add(new Product("94623429", db));
        }
    }

    public void generateProductPage(View view) {
        Intent intent = new Intent(this, ProductPage.class);
        TextView p_id = (TextView) view.findViewById(R.id.product_id);
        String product_id = p_id.getText().toString();
        intent.putExtra("p_id", product_id);
        startActivity(intent);
    }

}
