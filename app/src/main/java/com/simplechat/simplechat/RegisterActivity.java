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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText mName, mEmail, mPassword;
    private Button mRegister;
    private TextView mLoginTxt;

    private FirebaseAuth mAuth;
//    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Register Account");

        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mName = (EditText) findViewById(R.id.nameField);
        mEmail = (EditText) findViewById(R.id.emailField);
        mPassword = (EditText) findViewById(R.id.passwordField);
        mRegister = (Button) findViewById(R.id.registerBtn);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });
        mLoginTxt = (TextView) findViewById(R.id.loginTxt);

        mLoginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
            }
        });
    }

    private void startRegister() {
        final String name = mName.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mProgress.setMessage("Signing Up");
            mProgress.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
//                        String user_id = mAuth.getCurrentUser().getUid();
//                        DatabaseReference current_db = mDatabase.child(user_id);
//
//                        current_db.child("name").setValue(name);
//                        current_db.child("email").setValue(email);

                        mProgress.dismiss();
                        Intent mainIntent = new Intent (RegisterActivity.this, LoginActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                    }
                }
            });
        }else{
            Toast.makeText(RegisterActivity.this, "Please Fill Up Email and Password!", Toast.LENGTH_LONG).show();
        }

    }
}
