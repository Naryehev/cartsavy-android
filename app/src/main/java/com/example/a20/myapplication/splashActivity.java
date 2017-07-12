package com.example.a20.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by 20 on 7/11/2017.
 */

public class splashActivity  extends AppCompatActivity {
    private Button b1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        b1 = (Button) findViewById(R.id.loginBtn);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


            }
        });


    }
}
