package com.example.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    TextInputEditText userNameEdt, passwdEdt, cnfmpwdEdt;
    Button registerBtn;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    TextView logintxtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userNameEdt = findViewById(R.id.idEdtUserName);
        passwdEdt = findViewById(R.id.idEdtUserPassword);
        cnfmpwdEdt = findViewById(R.id.idEdtCnfmPsd);
        progressBar = findViewById(R.id.progress);
        logintxtv = findViewById(R.id.Tvlogin);
        registerBtn = findViewById(R.id.idBtnRgstr);
        firebaseAuth = FirebaseAuth.getInstance();

        logintxtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String userName = userNameEdt.getText().toString();
                String password = passwdEdt.getText().toString();
                String cnfmpsd = cnfmpwdEdt.getText().toString();
                
                if (!password.equals(cnfmpsd)){
                    Toast.makeText(RegistrationActivity.this, "Confirm Password Doesnot Matches with Password", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(cnfmpsd)){
                    Toast.makeText(RegistrationActivity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.createUserWithEmailAndPassword(userName,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                                finish();
                            }else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "Something Went Wrong!! Please try after sometime", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}