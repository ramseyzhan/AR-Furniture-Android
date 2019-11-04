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
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import android.widget.Toast;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bongjlee.arfurnitureapp.data.Cartprods;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;

public class ARViewPage extends AppCompatActivity {
    private static final String TAG = ARViewPage.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private MyArFragment arFragment;
    private Uri tarObject;
    private Anchor mainAnchor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        setContentView(R.layout.activity_ar_ui);

        arFragment = (MyArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);

//        Button clearButton = findViewById(R.id.clear_button);
//        clearButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

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
        if(Cartprods.name1 != null){
            ImageView product_t = new ImageView( this );
            product_t.setImageResource(R.drawable.num1);
            product_t.setContentDescription(Cartprods.name1);
            product_t.setOnClickListener(view -> {tarObject = Uri.parse(Cartprods.name1+".sfb");});
            gallery.addView(product_t);
        }

        if(Cartprods.name2!=null) {
            ImageView product_t = new ImageView(this);
            product_t.setImageResource(R.drawable.num2);
            product_t.setContentDescription(Cartprods.name2);
            product_t.setOnClickListener(view -> {
                tarObject = Uri.parse(Cartprods.name2+".sfb");
            });
            gallery.addView(product_t);
        }
        if(Cartprods.name3!=null){
            ImageView product_t = new ImageView(this);
            product_t.setImageResource(R.drawable.sofa_thumb);
            product_t.setContentDescription(Cartprods.name3);
            product_t.setOnClickListener(view -> {
                tarObject = Uri.parse(Cartprods.name3+".sfb");
            });
            gallery.addView(product_t);
        }
        if(Cartprods.name4!=null){
            ImageView product_t = new ImageView(this);
            product_t.setImageResource(R.drawable.sofa_thumb);
            product_t.setContentDescription(Cartprods.name4);
            product_t.setOnClickListener(view -> {
                tarObject = Uri.parse(Cartprods.name4+".sfb");
            });
            gallery.addView(product_t);
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
