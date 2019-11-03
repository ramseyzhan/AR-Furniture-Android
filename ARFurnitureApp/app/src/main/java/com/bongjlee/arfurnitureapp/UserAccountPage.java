package com.bongjlee.arfurnitureapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.bongjlee.arfurnitureapp.UserLogin.loggedInUser;

public class UserAccountPage extends AppCompatActivity {
    private static final String TAG = UserAccountPage.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_page);
        Intent intent = getIntent();
        String value = intent.getStringExtra("toAccount");
        if (loggedInUser != null) {
            EditText emailText = (EditText)findViewById(R.id.editText2);
            emailText.setText(loggedInUser.getEmail());
            EditText nameText = (EditText)findViewById(R.id.editText);
            nameText.setText(loggedInUser.getDisplayName());
        }
    }
}
