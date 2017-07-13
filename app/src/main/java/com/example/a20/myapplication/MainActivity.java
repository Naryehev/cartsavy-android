package com.example.a20.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
//add the import statements here to import the Button, TextView and View classes
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    //vars
    private EditText mEmailAddy;
    private EditText mPasswd;
    private TextView txtView;
    private Button b1;
    private Button forgotPass;
    private Button signUp;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mEmailAddy = (EditText) findViewById(R.id.usernameInput);
        mPasswd = (EditText) findViewById(R.id.passwordInput);
        txtView = (TextView) findViewById(R.id.statusNotif);
        b1 = (Button) findViewById(R.id.loginBtn);
        forgotPass = (Button) findViewById(R.id.fpBtn);
        signUp = (Button) findViewById(R.id.signupBtn);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        b1.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {

            signIn(mEmailAddy.getText().toString().trim(), mPasswd.getText().toString());

            String userV = mEmailAddy.getText().toString();
                 String passV = mPasswd.getText().toString();

        }
    });
        forgotPass.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                forgotPassword(mEmailAddy.getText().toString().trim());
            }
        });
        signUp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                createAccount(mEmailAddy.getText().toString().trim(), mPasswd.getText().toString());
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     *
     * @param forgotPassword
     * @return valid boolean whether email and password is inputed.
     */
    private boolean  validateForm(boolean forgotPassword){
        Boolean valid = true;

        String email= mEmailAddy.getText().toString();
        if (TextUtils.isEmpty(email)){
            mEmailAddy.setError("Required.");
            valid = false;
        }else{
            mEmailAddy.setError(null);
        }
        if (forgotPassword){

        }else{
            String password = mPasswd.getText().toString();
            if(TextUtils.isEmpty(password)){
                mPasswd.setError("Required.");
                valid = false;
            }else{
                mPasswd.setError(null);
            }
        }
        return valid;
    }

    /**
     * singsi n user with email and password while checking with firebase credintials.
     * @param email
     * @param password
     */
    private void signIn(String email, final String password){
        Log.d("TAG", "signIn:" + email);
        boolean isForgotPassword= false;
        if(validateForm(isForgotPassword)){
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());
                            ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                            dialog.setMessage("Signing In...");
                            dialog.show();
                            if(task.isSuccessful()){
                                Intent myIntent = new Intent(getApplicationContext(), ActivityTwo.class);
                                startActivity(myIntent);
                                finish();
                                dialog.dismiss();
                            }else{
                                Log.w("TAG", "signInWithEmail:failed", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });
        }
    }
    public static  void signOut(){
        FirebaseAuth.getInstance().signOut();
    }
    public void createAccount(final String email, String password){
        Log.d("TAG", "createAccount:"+ email);
        boolean isForgotPassword = false;
        if(validateForm(isForgotPassword)){
            final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            dialog.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("TAG", "createUserwithEmail:onComplete:" + task.isSuccessful());
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ActivityTwo.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Invalid email or password." + "Password must have at least 6 Characters",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });
        }

    }
    public  void forgotPassword(String emailAddress){
        boolean isForgotPassword = true;
        if(validateForm(isForgotPassword)){
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                String EMAIL_SENT = "Email Sent.";
                                Log.d("TAG", EMAIL_SENT);
                                Toast.makeText((MainActivity.this), EMAIL_SENT, Toast.LENGTH_LONG).show();
                            }else{
                                String NOT_USER = "Not a user. Please create an account.";
                                Toast.makeText(MainActivity.this, NOT_USER, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

}
