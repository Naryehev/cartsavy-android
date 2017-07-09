package com.example.a20.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.stream.JsonReader;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.*;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;


public class ActivityTwo extends AppCompatActivity {
    public scannedFood fooditem;
    private TextView test;
    private TextView nameDis;
    public String foodName = "broken";
    private ImageView foodThumb;
    public URL thumbnail ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        Intent intent = getIntent();
        String value1 = intent.getStringExtra("Username");
        String value2 = intent.getStringExtra("Password");
        test = (TextView) findViewById(R.id.test);
        nameDis = (TextView) findViewById(R.id.nameDis);
        foodThumb = (ImageView) findViewById(R.id.foodThumb);

    }
    public void scanNow(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }
    /**
     * function handle scan result
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            // display it on screen
            TextView format1 = (TextView) findViewById(R.id.scan_content);
            format1.setText("FORMAT: " + scanFormat);
            TextView content1 = (TextView) findViewById(R.id.scan_content);
            content1.setText("Barcode Number: " + scanContent);

            try {
                URL url = new URL("https://world.openfoodfacts.org/api/v0/product/"+ scanContent +".json");
                new foodAsyncTask(this).execute(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public class foodAsyncTask extends AsyncTask<URL, Void, scannedFood>{
        Context context;
        public foodAsyncTask(Context context) { this.context=context;}
        @Override
        protected scannedFood doInBackground(URL... params){
            try{
                URL url = params[0];
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream inStream = connection.getInputStream();
                InputStreamReader inStreamReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));
                Gson gson = new Gson();
                results scanfood = gson.fromJson(inStreamReader, results.class);
                fooditem = scanfood.getProdResults();
                return scanfood.getProdResults();

            } catch(IOException e){
                return null;
            }
        }
        @Override
        protected void onPostExecute(scannedFood scanfoods){
            if (scanfoods == null) {
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
                return;
            }
            fooditem=scanfoods;
            nameDis.setText("Product Name: \n" + fooditem.getProduct_name_en());
            test.setText("Ingredients: \n" + fooditem.getIngredients_text());
            thumbnail = fooditem.getImage_url();
            Picasso.with(context).load(String.valueOf(thumbnail)).into(foodThumb);

        }
    }


}