package com.bongjlee.arfurnitureapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bongjlee.arfurnitureapp.render.model3D.view.ModelActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bongjlee.arfurnitureapp.UserLogin.loggedInUser;


public class ProductPage extends AppCompatActivity {
    private TextView nameViewData;
    private TextView DescriptionViewData;
    private TextView styleViewData;
    private TextView shippingInfoViewData;
    private TextView productLinkViewData;
    private TextView priceViewData;
    private ImageView photoViewData;

    private String docId_t;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_product );

        Intent intent = getIntent();
        this.docId_t = getIntent().getStringExtra( "p_id" );

        nameViewData = findViewById( R.id.product_name );
        DescriptionViewData = findViewById( R.id.product_description );
        styleViewData = findViewById( R.id.product_styles );
        shippingInfoViewData = findViewById( R.id.shipping_info );
        priceViewData = findViewById( R.id.product_price );
        photoViewData = findViewById( R.id.product_photo );


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ToggleButton fav = (ToggleButton) findViewById( R.id.button_favorite );

        if (loggedInUser != null) {
            DocumentReference userRef = db.collection( "users" ).document( Integer.toString( user.getEmail().hashCode() ) );

            userRef.get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete( @NonNull Task<DocumentSnapshot> task ) {
                    if ( task.isSuccessful() ) {
                        DocumentSnapshot document = task.getResult();
                        if ( document.exists() ) {
                            List<String> favList = (ArrayList<String>) document.get( "UserDetails.favoriteList" );
                            if(favList != null){
                                for ( int i = 0; i < favList.size(); ++i ) {
                                    if ( favList.get( i ).equals( docId_t ) ) {
                                        fav.setChecked( true );
                                    }
                                }
                            }

                        }
                    }
                }
            } );
        } else {
            fav.setVisibility(View.GONE);
        }



        DocumentReference docRef = db.collection( "products" ).document( docId_t );
        docRef.get()
                .addOnSuccessListener( new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess( DocumentSnapshot documentSnapshot ) {
                        if ( documentSnapshot.exists() ) {
                            String photoId = documentSnapshot.getString( "photoId" );
                            String productDescription = documentSnapshot.getString( "description" );
                            String name = documentSnapshot.getString( "name" );
                            String style = documentSnapshot.getString( "style" );
                            String shippinginfo = documentSnapshot.getString( "shippingInfo" );
                            String price = documentSnapshot.getString( "price" );
                            nameViewData.setText( "Name: " + name );
                            priceViewData.setText( "Price: " + price );
                            DescriptionViewData.setText( "Description: " + productDescription );
                            styleViewData.setText( "Style: " + style );
                            shippingInfoViewData.setText( "Shipping information: " + shippinginfo );
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReference();

                            StorageReference spaceRef = storageRef.child( "images/" + photoId + ".jpg" );
                            try {
                                File localFile = File.createTempFile( "images", "jpg" );
                                spaceRef.getFile( localFile ).addOnSuccessListener( new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess( FileDownloadTask.TaskSnapshot taskSnapshot ) {
                                        photoViewData.setImageURI( Uri.fromFile( localFile ) );
                                    }
                                } ).addOnFailureListener( new OnFailureListener() {
                                    @Override
                                    public void onFailure( @NonNull Exception exception ) {
                                    }
                                } );
                            } catch ( IOException e ) {
                            }
                        }
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure( @NonNull Exception e ) {

                    }
                } );
    }

    public void GeneratAR( View view ) {
        Intent intent = new Intent( this, ARViewPage.class );

        intent.putExtra( "p_id", this.docId_t );
        startActivity( intent );
    }

    public void addFavorites( View view ) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ToggleButton favButton = (ToggleButton) findViewById( R.id.button_favorite );
        String userEmailHash = "";
        if ( user != null ) {
            userEmailHash = ( user.getEmail() );
            userEmailHash = Integer.toString( userEmailHash.hashCode() );

        } else {
            // No user is signed in
        }
        if ( favButton.isChecked() ) {

            if ( userEmailHash == "" ) {

            } else {
                final Map<String, Object> addFavToArray = new HashMap<>();
                addFavToArray.put( "UserDetails.favoriteList", FieldValue.arrayUnion( docId_t ) );
                db.collection( "users" ).document( userEmailHash ).update( addFavToArray );

            }
        } else {
            final Map<String, Object> addFavToArray = new HashMap<>();
            addFavToArray.put( "UserDetails.favoriteList", FieldValue.arrayRemove( docId_t ) );

            db.collection( "users" ).document( userEmailHash ).update( addFavToArray );

        }
    }

    public void Purchase( View view ) {
        startActivity( new Intent( ProductPage.this, BuyingPage.class ) );
    }

    public void back( View view ) {
        Intent intent = new Intent( ProductPage.this, HomePage.class );
        startActivity( intent );
    }

    public void editproduct( View view ) {
        Intent intent = new Intent( ProductPage.this, EditPage.class );
        intent.putExtra( "p_id", this.docId_t );
        startActivity( intent );
    }


    public void SeeReview( View view ) {
        Intent intent = new Intent( this, See_review_page.class );
        intent.putExtra( "p_id", this.docId_t );
        startActivity(intent);

    }

    public void viewModel( View view ) {
        Intent intent = new Intent(this, ModelActivity.class);
        startActivity(intent);
    }

    public void SubmitReview( View view ) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Submit_content = ( (EditText) findViewById( R.id.input_text ) ).getText().toString();
        String Submit_rating_hardcode = "3";

        db = FirebaseFirestore.getInstance();
        Map<String, Object> newReview = new HashMap<>();
        newReview.put( "review_content", Submit_content );
        newReview.put( "review_rating", Submit_rating_hardcode );
        newReview.put( "productID", this.docId_t );
        newReview.put( "userID", user.getEmail() );
        db.collection( "reviews" ).document( this.docId_t + user.getEmail() + Submit_content ).set( newReview );
    }
}