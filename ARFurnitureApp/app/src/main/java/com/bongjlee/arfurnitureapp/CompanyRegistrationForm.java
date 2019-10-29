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

public class CompanyRegistrationForm extends AppCompatActivity {
    private static final String TAG = CompanyRegistrationForm.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_registration_form);
        Intent intent = getIntent();
        String value = intent.getStringExtra("toCompanyReg");
    }
    public void sendInfo(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> newCompany = new HashMap<>();
        EditText text = (EditText)findViewById(R.id.company_name);
        newCompany.put("CompanyName", text.getText().toString());
        Map<String, Object> companyDetails = new HashMap<>();
        text = (EditText)findViewById(R.id.company_office_address);
        companyDetails.put("CompanyOfficeAddress", text.getText().toString());
        text = (EditText)findViewById(R.id.company_email);
        companyDetails.put("CompanyEmail", text.getText().toString());
        text = (EditText)findViewById(R.id.company_phone);
        companyDetails.put("CompanyPhone", text.getText().toString());
        text = (EditText)findViewById(R.id.company_postal);
        companyDetails.put("CompanyPostal", text.getText().toString());
        text = (EditText)findViewById(R.id.company_website);
        companyDetails.put("CompanyWebsite", text.getText().toString());
        newCompany.put("CompanyDetails", companyDetails);
        db.collection("company")
                .add(newCompany)
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
