package com.bongjlee.arfurnitureapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.bongjlee.arfurnitureapp.data.Company;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CompanyRegistrationForm extends AppCompatActivity {
    private static final String TAG = CompanyRegistrationForm.class.getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_company_registration_form );
        // Intent intent = getIntent();
        // String value = intent.getStringExtra("toCompanyReg");
    }

    public void sendInfo( View view ) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, String> companyDetails = new HashMap<>();

        String companyName = ( (EditText) findViewById( R.id.company_name ) ).getText().toString();
        String companyOfficeAddress = ( (EditText) findViewById( R.id.company_office_address ) ).getText().toString();
        String companyEmail = ( (EditText) findViewById( R.id.company_email ) ).getText().toString();
        String companyPhone = ( (EditText) findViewById( R.id.company_phone ) ).getText().toString();
        String companyPostal = ( (EditText) findViewById( R.id.company_postal ) ).getText().toString();
        String companyWebsite = ( (EditText) findViewById( R.id.company_website ) ).getText().toString();

        companyDetails.put( "CompanyOfficeAddress", companyOfficeAddress );
        companyDetails.put( "CompanyEmail", companyEmail );
        companyDetails.put( "CompanyPhone", companyPhone );
        companyDetails.put( "CompanyPostal", companyPostal );
        companyDetails.put( "CompanyWebsite", companyWebsite );

        Company company = new Company(
                companyName,
                companyDetails
        );

        db.collection( "company" )
                .document(
                        String.valueOf( companyName.hashCode() )
                )
                .set( company )
                .addOnSuccessListener(
                        documentReference -> Log.d(
                                TAG,
                                "DocumentSnapshot added."
                        )
                )
                .addOnFailureListener(
                        e -> Log.w( TAG, "Error adding document", e )
                );

        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);

    }
}
