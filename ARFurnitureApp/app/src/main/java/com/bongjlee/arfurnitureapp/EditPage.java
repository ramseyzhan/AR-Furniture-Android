package com.bongjlee.arfurnitureapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bongjlee.arfurnitureapp.data.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.UploadTask;


public class EditPage extends AppCompatActivity {
    private TextView nameViewData;
    private TextView DescriptionViewData;
    private TextView styleViewData;
    private TextView shippingInfoViewData;
    private TextView productLinkViewData;
    private TextView priceViewData;
    private ImageView photoViewData;
    private TextView modelName;
    private Button btnChoose, btnUpload,btnModel;
    private final int PICK_IMAGE_REQUEST = 71;
    private final int ACTIVITY_CHOOSE_FILE = 1;
    private String docId_t;
    private String photoId;
    private Uri prodImage;
    private Uri modelobj;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        this.docId_t = getIntent().getStringExtra("p_id");
        photoId = getIntent().getStringExtra("photoId");
        nameViewData = findViewById(R.id.product_name);
        modelName = (TextView) findViewById(R.id.modelName);
        DescriptionViewData = findViewById(R.id.product_description);
        styleViewData = findViewById(R.id.product_styles);
        shippingInfoViewData = findViewById(R.id.shipping_info);
        priceViewData = findViewById(R.id.product_price);
        photoViewData = findViewById(R.id.product_photo_edit);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnModel = (Button) findViewById(R.id.modelChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        Log.e("ram",photoId);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseModel(ACTIVITY_CHOOSE_FILE);
            }
        });
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
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

    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/jpeg");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST);
    }

    public void chooseModel(int resultCode) {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose a sfb file"),resultCode);
    }

    public void edit (View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //get the new product info

        String productName = ( (EditText) findViewById( R.id.product_name_edit ) ).getText().toString();
        String productDescription = ( (EditText) findViewById( R.id.product_description_edit) ).getText().toString();
        String productPrice = ( (EditText) findViewById( R.id.product_price_edit ) ).getText().toString();
        String productStyles = ( (EditText) findViewById( R.id.product_styles_edit ) ).getText().toString();
        String shippingInfo = ( (EditText) findViewById( R.id.shipping_info_edit ) ).getText().toString();

        DocumentReference docRef = db.collection("products").document(this.docId_t);
        docRef.update("productName", productName);
        docRef.update("productDescription", productDescription);
        docRef.update("ProductPrice", productPrice);
        docRef.update("productStyles", productStyles);
        docRef.update("shippingInfo", shippingInfo);

        docRef.update("iconId", uploadIcon());
        docRef.update("photoId", uploadImage());
        docRef.update("modelId", uploadModel());

        Intent intent = new Intent(EditPage.this, ProductPage.class);
        intent.putExtra("p_id", this.docId_t);
        startActivity(intent);
    }

    public void back (View view) {
        this.finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            prodImage = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), prodImage);
                bitmap = Bitmap.createScaledBitmap(bitmap, 150, 200, true);
                prodImage = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
                        UUID.randomUUID().toString(),null));
                photoViewData.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if(requestCode == ACTIVITY_CHOOSE_FILE && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            modelobj = data.getData();
            modelName.setText("Sfb select as:"+modelobj.getLastPathSegment());
        }
    }

    private String uploadImage() {
        if(prodImage != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            String imageID = UUID.randomUUID().toString();
            StorageReference ref = storageRef.child("images/"+ imageID+".jpg");
            ref.putFile(prodImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                        }

                    });
            return imageID;
        }
        return null;
    }

    private String uploadIcon() {
        if(prodImage != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            String iconID = UUID.randomUUID().toString();
            StorageReference ref = storageRef.child("icon/AR/"+ iconID+".jpg");

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), prodImage);
                bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                Uri prodicon = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
                        UUID.randomUUID().toString(),null));
                ref.putFile(prodicon)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                            }

                        });
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return iconID;
        }
        return null;
    }

    private String uploadModel() {
        if(modelobj != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            String modelId=UUID.randomUUID().toString();
            StorageReference ref = storageRef.child("ARdata/"+ modelId+".sfb");
            ref.putFile(modelobj)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                        }

                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
            return modelId;
        }
        return null;
    }
}

