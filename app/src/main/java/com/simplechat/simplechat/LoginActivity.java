package com.simplechat.simplechat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button mLoginBtn, mSignUpBtn;

    private ProgressDialog mProgress;

    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login Account");

        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if(firebaseAuth.getCurrentUser() == null){
//                    Intent loginIntent = new Intent(LoginActivity.this, RegisterActivity.class);
//                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(loginIntent);
//                }
//            }
//        };

        mEmail = (EditText) findViewById(R.id.emailField);
        mPassword = (EditText) findViewById(R.id.passwordField);

        mLoginBtn = (Button) findViewById(R.id.loginBtn);
        mSignUpBtn = (Button) findViewById(R.id.signUpBtn);

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

//        mAuth.addAuthStateListener(mAuthListener);
    }

    private void checkLogin() {

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            mProgress.setMessage("Loging In");
            mProgress.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mProgress.dismiss();
                        Intent mainIntent = new Intent (LoginActivity.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                    }else{
                        mProgress.dismiss();
                        Toast.makeText(LoginActivity.this, "Email or Password is Wrong, Please Register First!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
//            mProgress.dismiss();
            Toast.makeText(LoginActivity.this, "Please Fill Up Email and Password!", Toast.LENGTH_LONG).show();
        }
    }

}
