package com.bongjlee.arfurnitureapp.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public final class Company {
    private static final String TAG = Company.class.getSimpleName();
    //company id is hashed by company name
    private Map<String, String> companyDetails;
    private String companyName;

    public Map<String, String> getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails( Map<String, String> companyDetails ) {
        this.companyDetails = companyDetails;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Company(){}

    public Company( String companyName, Map<String, String> companyDetails ){
        this.companyName = companyName;
        this.companyDetails = companyDetails;
    }

    public Company( String companyId, FirebaseFirestore db){
        DocumentReference docRef = db.collection("products").document(companyId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String company_name = document.getString("companyName");
                        String email = document.getString( "companyDetails.CompanyEmail" );
                        String address = document.getString( "companyDetails.CompanyOfficeAddress" );
                        String website = document.getString("companyDetails.CompanyWebsite");
                        String phone = document.getString("companyDetails.CompanyPhone");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}
