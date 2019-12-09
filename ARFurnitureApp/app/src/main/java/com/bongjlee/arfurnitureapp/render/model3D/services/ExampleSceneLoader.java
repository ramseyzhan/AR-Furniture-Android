package com.bongjlee.arfurnitureapp.render.model3D.services;

import android.app.ProgressDialog;
import android.opengl.GLES20;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bongjlee.arfurnitureapp.render.model3D.model.Object3D;
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
	private String modelId;
	public ExampleSceneLoader(ModelActivity modelActivity,String modelId) {
		super(modelActivity);
		this.modelId=modelId;
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
//					Object3DData axis = Object3DBuilder.buildAxis().setId("axis");
//					axis.setColor(new float[] { 1.0f, 0, 0, 1.0f });
//					addObject(axis);

					Object3DData mymodel = Object3DBuilder.loadV5(parent.getAssets(), "models/", modelId+".obj");
					mymodel.setPosition(new float[] { 0f, 0f, 0f });
					mymodel.setColor(new float[] { 1.0f, 1.0f, 1f, 1.0f });
					mymodel.setDrawMode(GLES20.GL_TRIANGLE_FAN);
					addObject(mymodel);

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
