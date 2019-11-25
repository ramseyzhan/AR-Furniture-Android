package com.bongjlee.arfurnitureapp.data;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Order {
    private String order_id;
    private String user_id;
    private String product_id;
    private String timestamp;
    private String product_name;
    private String product_price;
    private String product_store;

    public String getProduct_photo_id() {
        return product_photo_id;
    }

    public void setProduct_photo_id(String product_photo_id) {
        this.product_photo_id = product_photo_id;
    }

    private String product_photo_id;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_store() {
        return product_store;
    }

    public void setProduct_store(String product_store) {
        this.product_store = product_store;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public Order(String order_id, FirebaseFirestore db)  {
        this.order_id = order_id;
        DocumentReference docRef = db.collection("orders").document(order_id);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            user_id = documentSnapshot.getString("userID");
                            product_id = documentSnapshot.getString("productID");
                            timestamp = documentSnapshot.getString("timestamp");
                        }
                    }
                });
        DocumentReference docRef2 = db.collection("products").document(product_id);
        docRef2.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            product_name = documentSnapshot.getString("productName");
                            product_price = documentSnapshot.getString("productPrice");
                            product_store = documentSnapshot.getString("productStore" );

                        }
                    }
                });
    }

}
