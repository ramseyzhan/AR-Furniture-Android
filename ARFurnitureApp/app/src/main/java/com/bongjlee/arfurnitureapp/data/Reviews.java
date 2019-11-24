package com.bongjlee.arfurnitureapp.data;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import androidx.annotation.NonNull;


public class Reviews {
    protected String review_id;
    protected String userID;
    protected String productID;
    protected String review_rating;
    protected String review_content;

    public Reviews(String review_id,FirebaseFirestore db)  {
        this.review_id = review_id;
        DocumentReference docRef = db.collection("reviews").document(review_id);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            review_content = documentSnapshot.getString("review_content");
                            review_rating = documentSnapshot.getString("review_rating");
                            userID = documentSnapshot.getString("userID");
                            productID = documentSnapshot.getString("productID");
                        }
                    }
                });
    }




}
