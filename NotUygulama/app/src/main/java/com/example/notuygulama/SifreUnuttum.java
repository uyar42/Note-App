package com.example.notuygulama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SifreUnuttum extends AppCompatActivity {

    private EditText msifreunuttum;
    private Button msifrekurtarbuton;
    private TextView mgobacktologin;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_unuttum);

        getSupportActionBar().hide();

        msifreunuttum=findViewById(R.id.unutsifre);
        msifrekurtarbuton=findViewById(R.id.sifrekurtarbutton);
        mgobacktologin=findViewById(R.id.gobacktologin);

        firebaseAuth = FirebaseAuth.getInstance();


        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SifreUnuttum.this,MainActivity.class);
                startActivity(intent);
            }
        });

        msifrekurtarbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = msifreunuttum.getText().toString().trim();
                if (mail.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Lütfen emailinizi giriniz", Toast.LENGTH_SHORT).show();
                }
                else{
                //
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Mail gönderildi , şifrenizi değişebilirsiniz", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(SifreUnuttum.this,MainActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Email bulunmamaktadır. Yanlış email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }
}