package com.example.chatapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
   EditText txtemail,txtpassword;
    Button btn_login,phoneloginbutton;
    private TextView needaccountlink,forgetpasswordlink;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Giriş Yap");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();

        txtemail = findViewById(R.id.email);
        txtpassword = findViewById(R.id.password);
        needaccountlink=findViewById(R.id.need_new_account_link);
        forgetpasswordlink= findViewById(R.id.forget_passport_link);
        btn_login = findViewById(R.id.btn_login);
        phoneloginbutton=findViewById(R.id.phone_login_button);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        phoneloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneLoginIntent= new Intent(LoginActivity.this,PhoneLoginActivity.class);
                startActivity(phoneLoginIntent);
            }
        });


        needaccountlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToRegisterActivity();

            }
        });
    }

    private void SendUserToRegisterActivity() {
        Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(registerIntent);
    }


    private void loginUser() {
        String email=txtemail.getText().toString();
        String password=txtpassword.getText().toString();

        if ( TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Email Alanı Boş Olamaz!",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Şifre Alanı Boş Olamaz!",Toast.LENGTH_LONG).show();

        }else{

            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Giriş Başarılı!",Toast.LENGTH_LONG).show();
                        Intent mainıntent=new Intent(LoginActivity.this,MainActivity.class);
                        mainıntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);/*12.video değişiklik*/
                        startActivity(mainıntent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"Giriş Başarısız!",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
