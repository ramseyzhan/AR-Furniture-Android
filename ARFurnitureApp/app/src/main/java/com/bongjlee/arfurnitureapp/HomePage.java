package com.bongjlee.arfurnitureapp;
//package com.example.ListDisplay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
import com.google.firebase.firestore.model.Document;

public class HomePage extends AppCompatActivity {
    private static final String TAG = HomePage.class.getSimpleName();
    private TextView nameViewData;
    private TextView DescriptionViewData;
    private TextView styleViewData;
    private TextView shippingInfoViewData;
    private TextView productLinkViewData;
    private TextView priceViewData;
    private ImageView photoViewData;
    //private TextView textViewData;


    private String name1;
    private String name2;
    private TextView nameViewData2;
    private TextView productLinkViewData2;
    private TextView priceViewData2;
    private ImageView photoViewData2;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        nameViewData = findViewById(R.id.product_name);
        DescriptionViewData = findViewById(R.id.product_description);
        styleViewData = findViewById(R.id.product_styles);
        shippingInfoViewData = findViewById(R.id.shipping_info);
        //productLinkViewData = findViewById(R.id.product_link);
        priceViewData = findViewById(R.id.product_price);
        photoViewData = findViewById(R.id.product_photo);
        productLinkViewData = findViewById(R.id.product_link);
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
        DocumentReference docRef = db.collection("products").document("1939035510");


        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String id = documentSnapshot.getString("photoID");
                            String productDescription = documentSnapshot.getString("productDescription");
                            String name = documentSnapshot.getString("productName");
                            String style = documentSnapshot.getString("productStyles");
                            String shippinginfo = documentSnapshot.getString("shippingInfo");
                            String price = documentSnapshot.getString("ProductPrice");
                            name1 = documentSnapshot.getString("DocId");
                            nameViewData.setText("Name: " + name);
                            priceViewData.setText("Price: " + price);
                            productLinkViewData.setText("Product Page: "+ name1);
                            //photoViewData.set
                            //DescriptionViewData.setText("Description: " + productDescription);
                            //styleViewData.setText("Style: " + style);
                            //shippingInfoViewData.setText("Shipping: " + shippinginfo);
                            Product a = new Product();

                        }else {

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        nameViewData2 = findViewById(R.id.product_name2);
        priceViewData2 = findViewById(R.id.product_price2);
        photoViewData2 = findViewById(R.id.product_photo2);
        productLinkViewData2 = findViewById(R.id.product_link2);
        docRef = db.collection("products").document("94623429");

        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String id = documentSnapshot.getString("photoID");
                            String productDescription = documentSnapshot.getString("productDescription");
                            String name = documentSnapshot.getString("productName");
                            String style = documentSnapshot.getString("productStyles");
                            String shippinginfo = documentSnapshot.getString("shippingInfo");
                            String price = documentSnapshot.getString("ProductPrice");
                            name2 = documentSnapshot.getString("DocId");
                            nameViewData2.setText("Name: " + name);
                            priceViewData2.setText("Price: " + price);
                            productLinkViewData2.setText("Description: "+productDescription);
                            Product a = new Product();

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


    public void generateProductPage(View view) {
        Intent intent = new Intent(this, ProductPage.class);
        intent.putExtra("name", name1);

        startActivity(intent);
    }
    public void generateProductPage2(View view) {
        Intent intent = new Intent(this, ProductPage.class);
        intent.putExtra("name", name2);

        startActivity(intent);
    }

}
