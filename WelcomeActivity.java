package com.example.chatapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {
    private Button btnstartlogin,btnstartsregister;

    FirebaseUser firebaseUser;


    public void init(){
        btnstartlogin=(Button) findViewById(R.id.btnstartlogin);
        btnstartsregister=(Button) findViewById(R.id.btnstartsregister);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null){
            Intent intneta=new Intent(WelcomeActivity.this,MainActivity.class);
            startActivity(intneta);
            finish();
        }

        btnstartlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlogin = new Intent(WelcomeActivity.this, LoginActivity.class);
               startActivity(intentlogin);
               finish();
            }
        });

      btnstartsregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentregister = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(intentregister);
                finish();
            }
        });
    }
}
