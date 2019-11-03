package com.bongjlee.arfurnitureapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class HomePage extends AppCompatActivity {
    private static final String TAG = HomePage.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        //rv.setHasFixedSize(true);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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
}
