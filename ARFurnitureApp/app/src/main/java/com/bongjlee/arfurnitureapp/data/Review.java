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


public class Review {
    private String review_id;
    private String productID;
    private String review_rating;
    private String review_content;
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getReview_rating() {
        return review_rating;
    }

    public void setReview_rating(String review_rating) {
        this.review_rating = review_rating;
    }

    public String getReview_content() {
        return review_content;
    }

    public void setReview_content(String review_content) {
        this.review_content = review_content;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public Review(String review_id, FirebaseFirestore db)  {
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
