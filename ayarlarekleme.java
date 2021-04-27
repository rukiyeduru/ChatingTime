package com.example.chatapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {

    private Button UpdateAccountSettings;
    private EditText userName,userStatus;
    private CircleImageView userProfileImage;

    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference Rootref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();
        Rootref=FirebaseDatabase.getInstance().getReference();

        init();

        UpdateAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Updatesetting();
            }
        });
    }

    private void init() {
        UpdateAccountSettings=(Button) findViewById(R.id.update_setting_button);
        userName=(EditText) findViewById(R.id.set_user_name);
        userStatus=(EditText) findViewById(R.id.set_profile_status);
        userProfileImage=(CircleImageView) findViewById(R.id.set_profile_image);

    }
    private void Updatesetting() {

        String setUsername=userName.getText().toString();
        String setstatus=userStatus.getText().toString();

        if(TextUtils.isEmpty(setUsername)){
            Toast.makeText(this,"Lütfen kullanıcı adınızı yazınız..",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(setstatus)){
            Toast.makeText(this,"Hakkımda..",Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String ,String> profileMap= new HashMap<>();
            profileMap.put("uid",currentUserID);
            profileMap.put("name",setUsername);
            profileMap.put("status",setstatus);
            Rootref.child("Users").child(currentUserID).setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        SendUserToMainActivity();

                        Toast.makeText(SettingActivity.this,"Profil Güncellendi..",Toast.LENGTH_SHORT).show();
                    }else{
                        String message=task.getException().toString();
                        Toast.makeText(SettingActivity.this,"Hata:"+message,Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(SettingActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }
}
