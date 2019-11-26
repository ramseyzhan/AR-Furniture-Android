package com.bongjlee.arfurnitureapp.data;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Product {
    public String id;
    public String name;
    public String price;
    public String description;
    public String style;
    public String shippingInfo;
    public String photoId;
    public String iconId;
    public String modelId;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice( String price ) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle( String style ) {
        this.style = style;
    }

    public String getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo( String shippingInfo ) {
        this.shippingInfo = shippingInfo;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId( String photoId ) {
        this.photoId = photoId;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId( String iconId ) {
        this.iconId = iconId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId( String modelId ) {
        this.modelId = modelId;
    }

    public Product(){}

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
                            modelId = documentSnapshot.getString("modelId");
                        }
                    }
                });
    }

}
