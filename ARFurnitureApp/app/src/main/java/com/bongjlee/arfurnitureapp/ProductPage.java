package com.bongjlee.arfurnitureapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bongjlee.arfurnitureapp.data.Product;
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
    private static final String TAG = ProductPage.class.getSimpleName();

    private TextView nameViewData;
    private TextView DescriptionViewData;
    private TextView styleViewData;
    private TextView shippingInfoViewData;
    private TextView productLinkViewData;
    private TextView priceViewData;
    private ImageView photoViewData;
    private String companyId;
    private String companyName;
    private String modelId;
    private String docId_t;
    private String photoId;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_product );

        this.docId_t = getIntent().getStringExtra( "p_id" );
        Product this_product = new Product( docId_t, db );

        nameViewData = findViewById( R.id.product_name );
        DescriptionViewData = findViewById( R.id.product_description );
        styleViewData = findViewById( R.id.product_styles );
        shippingInfoViewData = findViewById( R.id.shipping_info );
        priceViewData = findViewById( R.id.product_price );
        photoViewData = findViewById( R.id.product_photo );

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
                            photoId = documentSnapshot.getString( "photoId" );
                            String productDescription = documentSnapshot.getString( "description" );
                            String name = documentSnapshot.getString( "name" );
                            String style = documentSnapshot.getString( "style" );
                            String shippinginfo = documentSnapshot.getString( "shippingInfo" );
                            String price = documentSnapshot.getString( "price" );
                            companyId =  documentSnapshot.getString("companyId");
                            modelId= documentSnapshot.getString("modelId");
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
        /*
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Order new_order = new Order();
        //generate current time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String timestamp = dateFormat.format(date);
        //find company name by product company_id
        String product_store = this.companyId;

        String user_id = Integer.toString( user.getEmail().hashCode() );
        String product_id = this.docId_t;
        String product_name = ( (EditText) findViewById( R.id.product_name ) ).getText().toString();
        String product_price = ( (EditText) findViewById( R.id.product_price ) ).getText().toString();

        new_order.setTimestamp( timestamp );
        new_order.setProduct_store( product_store );
        new_order.setUser_id( user_id );
        new_order.setProduct_id( product_id );
        new_order.setProduct_name( product_name );
        new_order.setProduct_price( product_price );

        String order_id = Integer.toString( new_order.hashCode() );
        new_order.setOrder_id( order_id );

        db.collection( "orders" )
                .document(
                        order_id
                )
                .set( new_order )
                .addOnSuccessListener(
                        documentReference ->
                                Log.d(
                                        TAG,
                                        "DocumentSnapshot added."
                                )
                )
                .addOnFailureListener(
                        e -> Log.w( TAG, "Error adding document", e )
                );

         */
        Intent intent =  new Intent( ProductPage.this, BuyingPage.class );
        intent.putExtra("companyId", companyId);
        startActivity(intent );
    }

    public void back( View view ) {
        Intent intent = new Intent( ProductPage.this, HomePage.class );
        startActivity( intent );
    }

    public void editproduct( View view ) {
        Intent intent = new Intent( ProductPage.this, EditPage.class );
        intent.putExtra( "p_id", this.docId_t );
        Log.e("ram product",this.photoId );
        intent.putExtra( "photoId", this.photoId );

        startActivity( intent );
    }


    public void SeeReview( View view ) {
        Intent intent = new Intent( this, See_review_page.class );
        intent.putExtra( "p_id", this.docId_t );
        startActivity(intent);

    }

    public void viewModel( View view ) {
        Log.e("ram",modelId);
        Intent intent = new Intent(this, ModelActivity.class);
        intent.putExtra( "modelId", this.modelId );

        startActivity(intent);
    }

    public void SubmitReview( View view ) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Submit_content = ( (EditText) findViewById( R.id.input_text ) ).getText().toString();
        String Submit_rating_hardcode = "3";
        Map<String, Object> newReview = new HashMap<>();
        newReview.put( "review_content", Submit_content );
        newReview.put( "review_rating", Submit_rating_hardcode );
        newReview.put( "productID", this.docId_t );
        newReview.put( "userID", user.getEmail() );
        db.collection( "reviews" ).document( this.docId_t + user.getEmail() + Submit_content ).set( newReview );
    }
}