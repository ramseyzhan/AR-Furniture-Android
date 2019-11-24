package com.bongjlee.arfurnitureapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import android.view.View;
import android.widget.ListView;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.bongjlee.arfurnitureapp.UserLogin.loggedInUser;

import androidx.fragment.app.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bongjlee.arfurnitureapp.data.Product;
import com.bongjlee.arfurnitureapp.utils.productAdapter;

import com.google.firebase.firestore.model.Document;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;



public class HomePage extends AppCompatActivity {
    private static final String TAG = HomePage.class.getSimpleName();

    private ArrayList<Product> prodArrayList;
    private productAdapter prodAdapter;
    private FirebaseFirestore db;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        db = FirebaseFirestore.getInstance();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        prodArrayList = new ArrayList<Product>();
        prodAdapter = new productAdapter(this, prodArrayList,FirebaseStorage.getInstance().getReference());
        ListView lView = (ListView) findViewById(R.id.chattListView);
        lView.setAdapter(prodAdapter);
        refreshTimeline();
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String test = user.getEmail();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (loggedInUser != null) {
            inflater.inflate(R.menu.post_menu, menu);
        } else {
            inflater.inflate(R.menu.pre_menu, menu);
        }
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent;
        switch (item.getItemId()) {
            case R.id.product_page:
                myIntent = new Intent(HomePage.this, ProductPage.class);
                myIntent.putExtra("productPage", R.id.product_page);
                HomePage.this.startActivity(myIntent);
                return true;
            case R.id.my_products_page:
                myIntent = new Intent(HomePage.this, MyProductsPage.class);
                myIntent.putExtra("myProducts", R.id.my_products_page);
                HomePage.this.startActivity(myIntent);
                return true;
            case R.id.login_page:
                myIntent = new Intent(HomePage.this, UserLogin.class);
                myIntent.putExtra("login", R.id.login_page);
                HomePage.this.startActivity(myIntent);
                return true;
            case R.id.company_registration:
                myIntent = new Intent(HomePage.this, CompanyRegistrationForm.class);
                myIntent.putExtra("toCompanyReg", R.id.company_registration);
                HomePage.this.startActivity(myIntent);
                return true;
            case R.id.product_submission:
                myIntent = new Intent(HomePage.this, ProductSubmissionForm.class);
                myIntent.putExtra("toProductSub", R.id.product_submission);
                HomePage.this.startActivity(myIntent);
                return true;
            case R.id.account_page:
                myIntent = new Intent(HomePage.this, UserAccountPage.class);
                myIntent.putExtra(  "toAccount", R.id.account_page);
                HomePage.this.startActivity(myIntent);
                return true;
            case R.id.edit_account_page:
                myIntent = new Intent(HomePage.this, UserEdit.class);
                myIntent.putExtra("edit", R.id.edit_account_page);
                HomePage.this.startActivity(myIntent);
                return true;
            case R.id.log_off_page:
                myIntent = new Intent(HomePage.this, UserLogout.class);
                myIntent.putExtra("logOff", R.id.log_off_page);
                HomePage.this.startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refreshTimeline() {
        prodAdapter.clear();
        for (int i = 0; i < 20; i++) {
            if(i%2==0){
                prodAdapter.add(new Product("94623429",db));
            }
            else{
                prodAdapter.add(new Product("1939035510",db));
            }
        }

    }
    public void generateProductPage(View view) {
        Intent intent = new Intent(this, ProductPage.class);
        TextView p_id = (TextView) view.findViewById(R.id.product_id);
        String product_id = p_id.getText().toString();
        intent.putExtra("p_id", product_id);
        startActivity(intent);
    }

}
