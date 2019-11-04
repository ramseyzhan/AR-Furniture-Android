package com.bongjlee.arfurnitureapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

public class BuyingPage extends AppCompatActivity {
    private static final String TAG = BuyingPage.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying_page);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("company").document(
                "6IyAVIELJxa3RmfuiKyZ");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        TextView titleText = findViewById(R.id.CompanyName);
        TextView emailText = findViewById(R.id.CompanyEmail);
        TextView addressText = findViewById(R.id.CompanyAddress);
        TextView webText = findViewById(R.id.CompanyWeb);
        TextView phoneText = findViewById(R.id.CompanyPhone);
        //docRef.get
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        titleText.setText(document.getString("companyName"));
                        emailText.setText(document.getString("companyDetails.CompanyEmail"));
                        addressText.setText(document.getString("companyDetails.CompanyOfficeAddress"));
                        webText.setText(document.getString("companyDetails.CompanyWebsite"));
                        webText.setMovementMethod(LinkMovementMethod.getInstance());
                        phoneText.setText(document.getString("companyDetails.CompanyPhone"));

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        //TextView titleText = findViewById(R.id.CompanyName);
        //titleText.setText(docSnap.getString("companyName"));
    }
}