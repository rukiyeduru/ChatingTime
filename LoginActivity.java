package com.example.chatingtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Toolbar ActionbarLogin;
    private EditText txtEmail,txtPassword;
    private Button btnLogin;

    private FirebaseAuth auth;
    private FirebaseUser currentuser;

    private void init(){
        ActionbarLogin=(Toolbar) findViewById(R.id.ActionbarLogin);
        setSupportActionBar(ActionbarLogin);
        getSupportActionBar().setTitle("Giriş Yap");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        currentuser =auth.getCurrentUser();

        txtEmail=(EditText) findViewById(R.id.txtEmailLogin);
        txtPassword=(EditText) findViewById(R.id.txtPasswordLogin);
        btnLogin=(Button)findViewById(R.id.btnLogin);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser();
            }
        });
    }

    private void loginuser() {

        String email=txtEmail.getText().toString();
        String password=txtPassword.getText().toString();

        if(TextUtils.isEmpty(email)){

            Toast.makeText(this,"Email Alanı Boş Olamaz!",Toast.LENGTH_LONG).show();


        }else if(TextUtils.isEmpty(password)){

            Toast.makeText(this,"Şifre Alanı Boş Olamaz!",Toast.LENGTH_LONG).show();

        }else{

            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Giriş Başarılı!",Toast.LENGTH_LONG).show();
                        Intent tabIntent = new Intent(LoginActivity.this,TabActivity.class);
                        startActivity(tabIntent);
                        finish();

                    }else{
                        Toast.makeText(LoginActivity.this,"Giriş Başarısız!",Toast.LENGTH_LONG).show();

                    }
                }
            });

        }
    }
}
