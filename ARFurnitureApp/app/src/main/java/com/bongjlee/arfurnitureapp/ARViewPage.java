package com.bongjlee.arfurnitureapp;

import android.net.Uri;


import android.app.AlertDialog;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import android.view.MotionEvent;
import android.view.View;

import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import android.widget.Toast;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bongjlee.arfurnitureapp.data.Cartprods;
import com.bongjlee.arfurnitureapp.data.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ARViewPage extends AppCompatActivity {
    private static final String TAG = ARViewPage.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private MyArFragment arFragment;
    private Uri tarObject;
    private Anchor mainAnchor;
    private ArrayList<String> docs_gallery;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }
        docs_gallery = new ArrayList<String>();
        db = FirebaseFirestore.getInstance();

        String doc_id = getIntent().getStringExtra("p_id");
//        Log.e("ram","doc"+doc_id);
        docs_gallery.add(doc_id);

        docs_gallery.add("94623429");
        docs_gallery.add("20191124");
        docs_gallery.add("1939035510");
        docs_gallery.add("24112019");
        docs_gallery.add("20191125");

        setContentView(R.layout.activity_ar_ui);
        arFragment = (MyArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        createGallery();
        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                     if (plane.getType() != Plane.Type.HORIZONTAL_UPWARD_FACING) {
                         return;
                     }

                    Anchor newAnchor = hitResult.createAnchor();

                    setmainAnchor(newAnchor);
                    placeObject(arFragment, mainAnchor, tarObject);
                }
        );
    }

    private void setmainAnchor (Anchor newAnchor) {
        if (mainAnchor != null) {
            mainAnchor.detach();
        }

        mainAnchor = newAnchor;
    }

    private void createGallery() {
        LinearLayout gallery = findViewById(R.id.gallery_layout);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        for(String prod_id_t : docs_gallery) {
            ImageView product_t = new ImageView(this);

            DocumentReference docRef = db.collection("products").document(prod_id_t);

            docRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String iconId = documentSnapshot.getString("iconId");
                                String modelId = documentSnapshot.getString("modelId");
                                StorageReference spaceRef = storageRef.child("icon/AR/"+iconId+".jpg");
                                try{
                                    File localFile = File.createTempFile("images", "jpg");
                                    spaceRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {

                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            Log.e("ram","icon done");

                                            StorageReference ARmodel = storageRef.child("ARdata/"+modelId+".sfb");
                                            product_t.setImageURI(Uri.fromFile(localFile));
                                            try{
                                                File localModel = File.createTempFile("armodel", "sfb");
                                                ARmodel.getFile(localModel).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                        Log.e("ram","model done");
                                                        product_t.setOnClickListener(view -> {tarObject = Uri.fromFile(localModel);});
                                                        Log.e("ram","set done");

                                                        gallery.addView(product_t);

                                                    }
                                                });
                                            }
                                            catch (IOException e){
                                            }
                                        }
                                    });
                                }
                                catch (IOException e){
                                }

                            }
                        }
                    });

        }

    }

    private void placeObject(ArFragment arFragment, Anchor anchor, Uri model) {
        ModelRenderable.builder()
                .setSource(arFragment.getContext(), model)
                .build()
                .thenAccept(renderable -> addNodeToScene(arFragment, anchor, renderable))
                .exceptionally((throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder( this );
                    builder.setMessage(throwable.getMessage())
                            .setTitle("Error!");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return null;
                }));
    }

    private void addNodeToScene(ArFragment arFragment, Anchor anchor, Renderable renderable) {
        AnchorNode mainanchorNode = new AnchorNode(anchor);
        TransformableNode mainnode = new TransformableNode(arFragment.getTransformationSystem());
        mainnode.setRenderable(renderable);
        mainnode.setParent(mainanchorNode);
        arFragment.getArSceneView().getScene().addChild(mainanchorNode);
        mainnode.select();
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }

    public void clearAnchor(View view) {
        setmainAnchor(null);
    }
    public void exitAct(View view) {
        this.finish();
    }
}
