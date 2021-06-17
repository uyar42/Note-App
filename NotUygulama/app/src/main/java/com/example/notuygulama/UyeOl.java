package com.example.notuygulama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

public class UyeOl extends AppCompatActivity {


    private EditText mkayıtemail,mkayıtsifre;
    private RelativeLayout mkayıt;
    private TextView mgotologin;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_ol);

        getSupportActionBar().hide();

        mkayıtemail=findViewById(R.id.kayıtemail);
        mkayıtsifre=findViewById(R.id.kayıtsifre);
        mkayıt=findViewById(R.id.kayıtol);
        mgotologin=findViewById(R.id.gotologin);

        firebaseAuth=FirebaseAuth.getInstance();



        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UyeOl.this,MainActivity.class);
                startActivity(intent);
            }
        });

        mkayıt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mkayıtemail.getText().toString().trim();
                String pass = mkayıtsifre.getText().toString().trim();

                if (mail.isEmpty() || pass.isEmpty())
                 {
                    Toast.makeText(getApplicationContext(), "Lütfen gerekli yerleri doldurunuz", Toast.LENGTH_SHORT).show();
                }
                else if (pass.length()<7)
                {
                    Toast.makeText(getApplicationContext(), "Şifreniz 7 karakterden büyük olmalıdır", Toast.LENGTH_SHORT).show();
                }
                else{
                    //firebase kayıt
                    firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Kayıt Başarılı..!", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }
                            else
                                {
                               // Toast.makeText(getApplicationContext(), "Kayıt Başarısız..!", Toast.LENGTH_SHORT).show();

                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }

    //email doğrulama gönder
    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Doğrulama başarılı , giriş yapabilirsiniz", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(UyeOl.this,MainActivity.class));
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "Email doğrulaması başarısız", Toast.LENGTH_SHORT).show();
        }
    }
}