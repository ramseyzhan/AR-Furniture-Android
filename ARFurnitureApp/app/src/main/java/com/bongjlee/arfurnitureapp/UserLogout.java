package com.bongjlee.arfurnitureapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String value = intent.getStringExtra("logout");
        FirebaseAuth.getInstance().signOut();
        // Redirect
        Intent i = new Intent(UserLogout.this, HomePage.class);
        startActivity(i);
        finish();
    }
}
