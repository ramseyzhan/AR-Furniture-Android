package Yan.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


public class ProductPage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

    }


    public void postChatt(View view) {
        Log.e("asd", "postChatt: sdsd");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
