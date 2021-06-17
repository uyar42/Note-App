package com.example.notuygulama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private EditText mloginemail, mloginpass;
    RelativeLayout mlogin, mgotokayıtol;
    private TextView mgotosifreunuttum;

    FirebaseAuth firebaseAuth;

    ProgressBar mprogressbarmain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        mloginemail = findViewById(R.id.loginemail);
        mloginpass = findViewById(R.id.loginpassword);
        mlogin = findViewById(R.id.login);
        mgotosifreunuttum = findViewById(R.id.gotosifreunuttum);
        mgotokayıtol = findViewById(R.id.kayıtol);

        mprogressbarmain=findViewById(R.id.progressbarmain);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser!=null)
        {
            startActivity(new Intent(MainActivity.this,Notlar.class));
        }


        mgotokayıtol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UyeOl.class));
            }
        });

        mgotosifreunuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SifreUnuttum.class));
            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mloginemail.getText().toString().trim();
                String pass = mloginpass.getText().toString().trim();

                if (mail.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Lütfen gerekli yerleri doldurunuz", Toast.LENGTH_SHORT).show();
                } else {
                    //giriş

                    mprogressbarmain.setVisibility(View.VISIBLE);

                    firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                checkmailverfication();
                            } else {
                                Toast.makeText(getApplicationContext(), "Hesap bulunamamaktadır.!", Toast.LENGTH_SHORT).show();
                                mprogressbarmain.setVisibility(View.INVISIBLE);
                            }

                        }
                    });
                }

            }
        });
    }


    private void checkmailverfication() {
            FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

            if (firebaseUser.isEmailVerified()){
                Toast.makeText(getApplicationContext(), "Giriş yapıldı", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(MainActivity.this,Notlar.class));
            }
            else{
                mprogressbarmain.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Lütfen önce mailinizi doğrulayınız", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
            }
    }
}