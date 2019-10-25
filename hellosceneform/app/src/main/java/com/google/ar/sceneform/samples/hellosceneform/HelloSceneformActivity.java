/*
 * Copyright 2018 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.ar.sceneform.samples.hellosceneform;

import android.net.Uri;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import android.view.MotionEvent;
import android.view.View;

import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;

import android.widget.Toast;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
public class HelloSceneformActivity extends AppCompatActivity {
  private static final String TAG = HelloSceneformActivity.class.getSimpleName();
  private static final double MIN_OPENGL_VERSION = 3.0;

  private ArFragment arFragment;
  private ModelRenderable andyRenderable;
  private ModelRenderable chairRenderable;
  private int flag=0;

  private Anchor ourAnchor;

    private Uri tardObject;
    private enum AnchorState {
        NONE,
        HOSTING,
        HOSTED,
        RESOLVING,
        RESOLVED
    }
    private AnchorState ourAnchorState = AnchorState.NONE;
    private CustomArFragment ARfragment;

    @Override
  @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
  // CompletableFuture requires api level 24
  // FutureReturnValueIgnored is not valid
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (!checkIsSupportedDeviceOrFinish(this)) {
      return;
    }
    setContentView(R.layout.activity_ux);

    ARfragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
    ARfragment.getPlaneDiscoveryController().hide();  // Hide initial hand gesture
    ARfragment.getArSceneView().getScene().setOnUpdateListener(this::onUpdateFrame);

    createGallery();

    Button clearButton = findViewById(R.id.clear_button);
    clearButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setourAnchor(null);
        }
    });

    Button resolveButton = findViewById(R.id.resolve_button);

    resolveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ourAnchor != null) {
                return;
            }
        }
    });

        // When you build a Renderable, Sceneform loads its resources in the background while returning
    // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
    ModelRenderable.builder()
        .setSource(this, R.raw.andy)
        .build()
        .thenAccept(renderable -> andyRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

      ModelRenderable.builder()
              .setSource(this, R.raw.chair)
              .build()
              .thenAccept(renderable -> chairRenderable = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });


    arFragment.setOnTapArPlaneListener(
        (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
          if (andyRenderable == null) {
            return;
          }

          // Create the Anchor.
          Anchor anchor = hitResult.createAnchor();
          AnchorNode anchorNode = new AnchorNode(anchor);
          anchorNode.setParent(arFragment.getArSceneView().getScene());

          Log.e(TAG, "On Tap");
          flag++;
          Log.e(TAG, Integer.toString(flag));

          if(flag%2==0){
              TransformableNode andy = new TransformableNode(arFragment.getTransformationSystem());
              andy.setParent(anchorNode);
              andy.setRenderable(andyRenderable);
              andy.select();
          }
          else{
              TransformableNode chair = new TransformableNode(arFragment.getTransformationSystem());
              chair.setParent(anchorNode);
              chair.setRenderable(chairRenderable);
              chair.select();
          }
        });
  }

  /**
   * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
   * on this device.
   *
   * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
   *
   * <p>Finishes the activity if Sceneform can not run
   */
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

    private void setourAnchor (Anchor newAnchor) {
        if (ourAnchor != null) {
            ourAnchor.detach();
        }

        ourAnchor = newAnchor;
        ourAnchorState = AnchorState.NONE;
    }

    private void createGallery() {
        LinearLayout gallery = findViewById(R.id.gallery_layout);

        ImageView chair = new ImageView( this );
        chair.setImageResource(R.drawable.ic_launcher);
        chair.setContentDescription("chair");
        chair.setOnClickListener(view -> {tardObject = Uri.parse("chair.sfb");});
        gallery.addView(chair);
    }
}
