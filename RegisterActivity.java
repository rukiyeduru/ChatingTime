package com.example.chatingtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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
        auth=FirebaseAuth.getInstance();/*istediğimiz kontolü oluştuduk*/

        txtUsername=(EditText) findViewById(R.id.txtUsernameRegister);
        txtEmail=(EditText) findViewById(R.id.txtEmailRegister);
        txtPassword=(EditText) findViewById(R.id.txtPassowrdRegister);
        btnRegister=(Button) findViewById(R.id.btnRegister);
        btnLoginRegister=(Button) findViewById(R.id.btnLoginRegister);
    }


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }
}
