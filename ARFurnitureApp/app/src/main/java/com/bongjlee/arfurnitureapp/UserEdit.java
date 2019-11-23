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

import java.util.Arrays;
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

        String email_text = ((EditText)findViewById(R.id.email)).getText().toString();
        newUser.put("Email", email_text);

        Map<String, Object> userDetails = new HashMap<>();

        String person_name = ((EditText)findViewById(R.id.person_name)).getText().toString();
        userDetails.put("Name", person_name);

        String gender = ((EditText)findViewById(R.id.gender)).getText().toString();
        userDetails.put("Gender", gender);

        String age = ((EditText)findViewById(R.id.age)).getText().toString();
        userDetails.put("Age", age);

        String phone = ((EditText)findViewById(R.id.phone)).getText().toString();
        userDetails.put("PhoneNumber", phone);

        String postal = ((EditText)findViewById(R.id.postal_address)).getText().toString();
        userDetails.put("PostalAddress", postal);

        userDetails.put("favoriteList", Arrays.asList());

        // Store
        newUser.put("UserDetails", userDetails);
        db.collection("users")
                .document(email_text).set(newUser)
                .addOnSuccessListener(
                        documentReference -> Log.d(
                                TAG,
                                "DocumentSnapshot added with name: " + email_text
                        )
                )
                .addOnFailureListener(
                        e -> Log.w( TAG, "Error adding document", e )
                );
        Intent i = new Intent(UserEdit.this, HomePage.class);
        startActivity(i);
    }
}
