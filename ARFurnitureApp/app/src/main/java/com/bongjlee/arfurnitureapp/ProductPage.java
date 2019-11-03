package com.bongjlee.arfurnitureapp;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bongjlee.arfurnitureapp.data.Cartprods;


public class ProductPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent intent = getIntent();
        String value = intent.getStringExtra("productPage");
    }


    public void postChatt(View view) {
        Intent intent = new Intent(this, ARViewPage.class);
        Cartprods.name1 ="sofa";
        Cartprods.name2 ="chair";
        startActivity(intent);
    }
}
