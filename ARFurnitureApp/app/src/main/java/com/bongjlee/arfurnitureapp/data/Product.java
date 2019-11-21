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


public class Product {
    public String id;
    public String name;
    public String price;
    public String description;
    public String style;
    public String shippingInfo;
    public String photoId;
    public String iconId;
    public Product(String prod_id,FirebaseFirestore db)  {
        this.id = prod_id;
        DocumentReference docRef = db.collection("products").document(prod_id);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            photoId = documentSnapshot.getString("photoId");
                            description = documentSnapshot.getString("productDescription");
                            name = documentSnapshot.getString("productName");
                            style = documentSnapshot.getString("productStyles");
                            shippingInfo = documentSnapshot.getString("shippingInfo");
                            price = documentSnapshot.getString("ProductPrice");
                            iconId = documentSnapshot.getString("iconId");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

}
