package com.bongjlee.arfurnitureapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bongjlee.arfurnitureapp.data.Product;
import com.bongjlee.arfurnitureapp.data.Review;
import com.bongjlee.arfurnitureapp.utils.productAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;


public class order_page extends AppCompatActivity {
    private static final String TAG = order_page.class.getSimpleName();

    private ArrayList<Product> prodArrayList;
    private productAdapter prodAdapter;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_page);


        db = FirebaseFirestore.getInstance();
        prodArrayList = new ArrayList<Product>();
        prodAdapter = new productAdapter(this, prodArrayList,FirebaseStorage.getInstance().getReference());
        ListView lView = (ListView) findViewById(R.id.chattListView);
        lView.setAdapter(prodAdapter);
        refreshTimeline();
    }

    private void refreshTimeline() {
        prodAdapter.clear();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        CollectionReference ordersRef = db.collection("orders");
        ordersRef.whereEqualTo("userID",user.getUid()).orderBy("timestamp")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                prodAdapter.add(new Product(document.get("usrID").toString(),db));
                            }
                        }
                    }
                });

        DocumentReference userRef = db.collection("users").document(Integer.toString(user.getEmail().hashCode()));
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<String> favList = (ArrayList<String>) document.get("UserDetails.favoriteList");
                        for (int i = 0; i < favList.size(); ++i) {

                        }
                    }
                }
            }
        });
    }
    public void generateProductPage(View view) {
        Intent intent = new Intent(this, ProductPage.class);
        TextView p_id = (TextView) view.findViewById(R.id.product_id);
        String product_id = p_id.getText().toString();
        intent.putExtra("p_id", product_id);
        startActivity(intent);
    }

}
