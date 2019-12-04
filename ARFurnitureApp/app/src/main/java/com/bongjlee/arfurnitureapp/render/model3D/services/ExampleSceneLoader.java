package com.bongjlee.arfurnitureapp.render.model3D.services;

import android.app.ProgressDialog;
import android.opengl.GLES20;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bongjlee.arfurnitureapp.render.model3D.model.Object3DBuilder;
import com.bongjlee.arfurnitureapp.render.model3D.model.Object3DData;
import com.bongjlee.arfurnitureapp.render.model3D.view.ModelActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class loads a 3D scene as an example of what can be done with the app
 * 
 * @author andresoviedo
 *
 */
public class ExampleSceneLoader extends SceneLoader {

	public ExampleSceneLoader(ModelActivity modelActivity) {
		super(modelActivity);
	}

	public void init() {
		super.init();
		new AsyncTask<Void, Void, Void>() {

			ProgressDialog dialog = new ProgressDialog(parent);
			List<Exception> errors = new ArrayList<Exception>();

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				dialog.setCancelable(false);
				dialog.setMessage("Loading demo...");
				dialog.show();
			}

			@Override
			protected Void doInBackground(Void... params) {
				try {
					// 3D Axis
					Object3DData axis = Object3DBuilder.buildAxis().setId("axis");
					axis.setColor(new float[] { 1.0f, 0, 0, 1.0f });
					addObject(axis);

					// test loading object
					try {
						// this has no color array
						Object3DData android = Object3DBuilder.loadV5(parent.getAssets(), "models/", "android.obj");
						android.generateVertexColorsArrayBuffer();
						android.setPosition(new float[] { 0f, 0f, 0f });
						android.setColor(new float[] { 1.0f, 0.5f, 0f, 1.0f });
						addObject(android);
					} catch (Exception ex) {
						errors.add(ex);
					}


					// test loading object
					try {
						// this has no color array
						Object3DData obj51 = Object3DBuilder.loadV5(parent.getAssets(), "models/", "teapot.obj");
						obj51.setPosition(new float[] { -2f, 0f, 0f });
						obj51.setColor(new float[] { 1.0f, 1.0f, 0f, 1.0f });
						addObject(obj51);
					} catch (Exception ex) {
						errors.add(ex);
					}

					// test loading object with materials
					try {
						// this has color array
						Object3DData obj52 = Object3DBuilder.loadV5(parent.getAssets(), "models/", "cube.obj");
						obj52.setPosition(new float[] { 2f, -2f, 0f });
						obj52.setColor(new float[] { 0.0f, 1.0f, 1f, 1.0f });
						addObject(obj52);
					} catch (Exception ex) {
						errors.add(ex);
					}

					// test loading object made of polygonal faces
					try {
						// this has heterogeneous faces
						Object3DData obj53 = Object3DBuilder.loadV5(parent.getAssets(), "models/", "ToyPlane.obj");
						obj53.centerAndScale(2.0f);
						obj53.setPosition(new float[] { 2f, 0f, 0f });
						obj53.setColor(new float[] { 1.0f, 1.0f, 1f, 1.0f });
						obj53.setDrawMode(GLES20.GL_TRIANGLE_FAN);
						addObject(obj53);
					} catch (Exception ex) {
						errors.add(ex);
					}
				} catch (Exception ex) {
					errors.add(ex);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				if (!errors.isEmpty()) {
					StringBuilder msg = new StringBuilder("There was a problem loading the data");
					for (Exception error : errors) {
						Log.e("Example", error.getMessage(), error);
						msg.append("\n" + error.getMessage());
					}
					Toast.makeText(parent.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
				}
			}
		}.execute();
	}
}
