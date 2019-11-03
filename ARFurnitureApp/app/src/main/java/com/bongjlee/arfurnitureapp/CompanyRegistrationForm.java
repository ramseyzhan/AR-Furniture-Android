package com.bongjlee.arfurnitureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bongjlee.arfurnitureapp.data.Company;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CompanyRegistrationForm extends AppCompatActivity {
    private static final String TAG = CompanyRegistrationForm.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_registration_form);
        // Intent intent = getIntent();
        // String value = intent.getStringExtra("toCompanyReg");
    }

    public void sendInfo(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, String> companyDetails = new HashMap<>();

        EditText companyName            = findViewById(R.id.company_name);
        EditText companyOfficeAddress   = findViewById(R.id.company_office_address);
        EditText companyEmail           = findViewById(R.id.company_email);
        EditText companyPhone           = findViewById(R.id.company_phone);
        EditText companyPostal          = findViewById(R.id.company_postal);
        EditText companyWebsite         = findViewById(R.id.company_website);

        companyDetails.put("CompanyOfficeAddress", companyOfficeAddress.getText().toString());
        companyDetails.put("CompanyEmail", companyEmail.getText().toString());
        companyDetails.put("CompanyPhone", companyPhone.getText().toString());
        companyDetails.put("CompanyPostal", companyPostal.getText().toString());
        companyDetails.put("CompanyWebsite", companyWebsite.getText().toString());

        Company company = new Company(
                companyDetails,
                companyName.getText().toString()
        );

        db.collection("company")
                .add(company)
                .addOnSuccessListener(
                        documentReference -> {
                            Log.d(
                                    TAG,
                                    "DocumentSnapshot added with ID: " + documentReference.getId()
                            );

                        }
                )
                .addOnFailureListener(
                        e -> Log.w(TAG, "Error adding document", e)
                );
    }
}
