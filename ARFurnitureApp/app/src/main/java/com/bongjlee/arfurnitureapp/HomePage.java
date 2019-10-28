package com.bongjlee.arfurnitureapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.company_registration:
                Intent myIntent = new Intent(HomePage.this, CompanyRegistrationForm.class);
                myIntent.putExtra("toCompanyReg", R.id.company_registration);
                HomePage.this.startActivity(myIntent);
                return true;
            case R.id.product_submission:
                myIntent = new Intent(HomePage.this, MainActivity.class);
                myIntent.putExtra("toProductSub", R.id.product_submission);
                HomePage.this.startActivity(myIntent);
            case R.id.acc_page:
                myIntent = new Intent(HomePage.this, UserAccountPage.class);
                myIntent.putExtra(  "toAccount", R.id.acc_page);
                HomePage.this.startActivity(myIntent);
            case R.id.sign_up_page:
                myIntent = new Intent(HomePage.this, UserSignUp.class);
                myIntent.putExtra("signUp", R.id.sign_up_page);
                HomePage.this.startActivity(myIntent);
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
