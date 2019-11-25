package com.bongjlee.arfurnitureapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bongjlee.arfurnitureapp.data.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import android.widget.Button;
import android.graphics.Bitmap;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.app.ProgressDialog;
import android.net.Uri;
import android.widget.ImageView;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class ProductSubmissionForm extends AppCompatActivity {
    private static final String TAG = ProductSubmissionForm.class.getSimpleName();
    private final int PICK_IMAGE_REQUEST = 71;
    private final int ACTIVITY_CHOOSE_FILE = 1;
    private Button btnChoose, btnUpload,btnModel;
    private ImageView graphView;
    private Uri prodImage;
    private Uri modelobj;
    private Uri modelmtl;
    private Uri modelsfa;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private TextView modelName;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_product_submission );
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnModel = (Button) findViewById(R.id.modelChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        graphView = (ImageView) findViewById(R.id.graphView);
        modelName = (TextView) findViewById(R.id.modelName);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInfo();
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

    }

    public void sendInfo() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Product product = new Product("123",db);

//        TODO: set up the upload
//        product.name=( (EditText) findViewById( R.id.product_name ) ).getText().toString();


        uploadImage();

//        db.collection( "products" )
//                .document(
//                        String.valueOf( productName.hashCode() )
//                )
//                .set( product )
//                .addOnSuccessListener(
//                        documentReference ->
//                                Log.d(
//                                        TAG,
//                                        "DocumentSnapshot added with ID: " + documentReference.toString()
//                                )
//                )
//                .addOnFailureListener(
//                        e -> Log.w( TAG, "Error adding document", e )
//                );

    }

    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST);
    }

    public void chooseModel(int resultCode) {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose a file"),resultCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            prodImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), prodImage);
                graphView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if(requestCode == ACTIVITY_CHOOSE_FILE && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            String filePath = data.getData().getLastPathSegment();;
            modelName.setText("OBJ select as:"+filePath);
        }
    }

    private void uploadImage() {
        if(prodImage != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageRef.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(prodImage)
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
        }
    }
}
