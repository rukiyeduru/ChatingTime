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

public class RegisterActivity extends AppCompatActivity {
    private Toolbar actionbarRegister;
    private EditText txtUsername, txtEmail,txtPassword;
    private Button btnRegister,btnLoginRegister;

    private FirebaseAuth auth;/*firebase de kontol edicez*/

    public void init(){/*kontollerimizi oluşturmak için */

        actionbarRegister=(Toolbar) findViewById(R.id.actionbarRegister);
        setSupportActionBar(actionbarRegister);
        getSupportActionBar().setTitle("Hesap Oluştur");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth=FirebaseAuth.getInstance();/*istediğimiz kontolü oluştuduk*/

        txtUsername=(EditText) findViewById(R.id.txtUsernameRegister);
        txtEmail=(EditText) findViewById(R.id.txtEmailRegister);
        txtPassword=(EditText) findViewById(R.id.txtPassowrdRegister);
        btnRegister=(Button) findViewById(R.id.btnRegister);
        btnLoginRegister=(Button) findViewById(R.id.btnLoginRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {/*verileriçekiyoruz*/
        String username = txtUsername.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        if(TextUtils.isEmpty(email)){

            Toast.makeText(this,"Email Alanı Boş Olmaz!",Toast.LENGTH_LONG).show();

        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Şifre Alanı Boş Olamaz!",Toast.LENGTH_LONG).show();

        }else{

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())/*görev başarılı ise yan e mail ve şifre girildiyse*/ {

                        Toast.makeText(RegisterActivity.this,"Hesap Oluşturdunuz !",Toast.LENGTH_LONG).show();
                        Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(loginIntent);
                        finish();

                    }else{
                        Toast.makeText(RegisterActivity.this,"Bir Hata Oluştu !",Toast.LENGTH_LONG).show();
                    }

                }
            });

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

    }
}
