package com.bongjlee.arfurnitureapp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bongjlee.arfurnitureapp.data.Review;
import com.bongjlee.arfurnitureapp.utils.reviewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;


public class See_review_page extends AppCompatActivity {
    private static final String TAG = See_review_page.class.getSimpleName();

    private ArrayList<String> reviewArrayList;
    private reviewAdapter reviewAdapter;
    private FirebaseFirestore db;

    private String docId_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        Intent intent = getIntent();
        this.docId_t = getIntent().getStringExtra("p_id");

        db = FirebaseFirestore.getInstance();
        reviewArrayList = new ArrayList<String>();
        reviewAdapter = new reviewAdapter(this, reviewArrayList,FirebaseStorage.getInstance().getReference());
        ListView lView = (ListView) findViewById(R.id.chattListView);
        lView.setAdapter(reviewAdapter);
        refreshTimeline();
    }

    private void refreshTimeline() {
        reviewAdapter.clear();
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        CollectionReference reviewRef = db.collection("reviews");
        reviewRef.whereEqualTo("productID",docId_t)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                reviewAdapter.add(document.getId());
                            }
                        }
                    }
                });
    }
}
