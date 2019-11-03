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

public class UserEdit extends AppCompatActivity {
    private static final String TAG = UserEdit.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        Intent intent = getIntent();
        String value = intent.getStringExtra("editUser");
    }
    public void sendInfo(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> newUser = new HashMap<>();

        EditText text = findViewById(R.id.email);
        newUser.put("Email", text.getText().toString());

        Map<String, Object> userDetails = new HashMap<>();
        text = findViewById(R.id.person_name);
        userDetails.put("Name", text.getText().toString());
        text = findViewById(R.id.gender);
        userDetails.put("Gender", text.getText().toString());
        text = findViewById(R.id.age);
        userDetails.put("Age", text.getText().toString());
        text = findViewById(R.id.phone);
        userDetails.put("PhoneNumber", text.getText().toString());
        text = findViewById(R.id.postal_address);
        userDetails.put("PostalAddress", text.getText().toString());

        // Store
        newUser.put("UserDetails", userDetails);
        db.collection("users")
                .add(newUser)
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
