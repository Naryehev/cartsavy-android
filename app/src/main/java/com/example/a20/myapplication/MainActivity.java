package com.example.a20.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
//add the import statements here to import the Button, TextView and View classes
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Button b1 = (Button) findViewById(R.id.loginBtn);

        b1.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
                EditText usernameV = (EditText) findViewById(R.id.usernameInput);
                EditText passwordV = (EditText) findViewById(R.id.passwordInput);
                TextView txtView = (TextView) findViewById(R.id.statusNotif);

            String userV = usernameV.getText().toString();
            String passV = passwordV.getText().toString();

            Intent myIntent = new Intent(MainActivity.this, ActivityTwo.class);
                myIntent.putExtra("Username", userV);
                myIntent.putExtra("Password", passV);
                startActivity(myIntent);


        }
    });

    }

}
