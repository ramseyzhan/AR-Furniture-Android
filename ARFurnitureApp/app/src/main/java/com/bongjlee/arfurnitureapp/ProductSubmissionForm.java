package com.bongjlee.arfurnitureapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bongjlee.arfurnitureapp.data.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;


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
    private String CompanyId;
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
                sendInfo(v);
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
        CompanyId = "1715127153";
    }

    public void sendInfo( View view ) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Product product = new Product();

        String productID = UUID.randomUUID().toString();
        String productName = ( (EditText) findViewById( R.id.product_name ) ).getText().toString();
        String productDescription = ( (EditText) findViewById( R.id.product_description ) ).getText().toString();
        String productPrice = ( (EditText) findViewById( R.id.product_price ) ).getText().toString();
        String productStyles = ( (EditText) findViewById( R.id.product_styles ) ).getText().toString();
        String shippingInfo = ( (EditText) findViewById( R.id.shipping_info ) ).getText().toString();

        product.setId( productID );
        product.setName( productName );
        product.setDescription( productDescription );
        product.setPrice( productPrice );
        product.setStyle( productStyles );
        product.setShippingInfo( shippingInfo );
        product.setCompanyId(CompanyId);

        product.setPhotoId(uploadImage());
        product.setIconId(uploadIcon());
        product.setModelId(uploadModel());

        db.collection( "products" )
                .document(
                        productID
                )
                .set( product )
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

        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            prodImage = data.getData();
            try {

//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), prodImage);
//                bitmap = Bitmap.createScaledBitmap(bitmap, 150, 200, true);
//                prodImage = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
//                        null,null));
                graphView.setImageURI(prodImage);
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
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), prodImage);
//                bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
//                Uri prodicon = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
//                        null,null));
                ref.putFile(prodImage)
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
