package com.example.firebasecrud;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText userNameEdt, psdEdt;
    Button loginBtn;
    ProgressBar mprogressbar;
    TextView registerTv;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameEdt = findViewById(R.id.idEdtUserName);
        psdEdt = findViewById(R.id.idEdtUserPassword);
        loginBtn = findViewById(R.id.idBtnRgstr);
        mprogressbar = findViewById(R.id.progress);
        registerTv = findViewById(R.id.Tvlogin);
        firebaseAuth = FirebaseAuth.getInstance();

        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mprogressbar.setVisibility(View.VISIBLE);
                String userName = userNameEdt.getText().toString();
                String psd = psdEdt.getText().toString();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(psd)) {
                    Toast.makeText(LoginActivity.this, "Please Enter Your credentials to continue...", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(userName, psd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mprogressbar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Login Successful..", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                mprogressbar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            this.finish();
        }
    }
}