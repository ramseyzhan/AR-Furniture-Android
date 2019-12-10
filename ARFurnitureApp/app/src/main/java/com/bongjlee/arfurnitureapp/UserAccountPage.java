package com.bongjlee.arfurnitureapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.bongjlee.arfurnitureapp.UserLogin.loggedInUser;

public class UserAccountPage extends AppCompatActivity {
    private static final String TAG = UserAccountPage.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_page);
        Intent intent = getIntent();
        String value = intent.getStringExtra("toAccount");
        if (loggedInUser != null) {
            TextView emailText = (TextView)findViewById(R.id.email);
            emailText.setText("Email: " + loggedInUser.getEmail());

        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("users").document(Integer.toString(loggedInUser.getEmail().hashCode()));
        TextView addressText = (TextView)findViewById(R.id.address);
        TextView phoneNumber = (TextView) findViewById((R.id.phone_number));
        TextView nameText = (TextView)findViewById(R.id.name);
        userRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String dbAdd = documentSnapshot.getString("UserDetails.PostalAddress");
                            String dbPhone = documentSnapshot.getString("UserDetails.PhoneNumber");
                            String dbName = documentSnapshot.getString("UserDetails.Name");
                            nameText.setText("Name: " + dbName);
                            addressText.setText("Address: " + dbAdd);
                            phoneNumber.setText("Phone Number: " + dbPhone);
                        }
                    }
                });
    }
    public void generateOrder (View view){
        startActivity(new Intent(UserAccountPage.this, order_page.class));
    }
    public void generateFav (View view){
        startActivity(new Intent(UserAccountPage.this, favorite_page.class));
    }
    public void goHome (View view){
        startActivity(new Intent(UserAccountPage.this, HomePage.class));
    }
}
