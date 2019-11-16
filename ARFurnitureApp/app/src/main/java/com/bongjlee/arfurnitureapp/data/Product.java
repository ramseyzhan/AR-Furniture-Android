package com.bongjlee.arfurnitureapp.data;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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

    public Product(String prod_id,FirebaseFirestore db) {
        this.id = prod_id;
        DocumentReference docRef = db.collection("products").document("1939035510");
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            photoId = documentSnapshot.getString("photoID");
                            description = documentSnapshot.getString("productDescription");
                            name = documentSnapshot.getString("productName");
                            style = documentSnapshot.getString("productStyles");
                            shippingInfo = documentSnapshot.getString("shippingInfo");
                            price = documentSnapshot.getString("ProductPrice");
                        }else {

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
