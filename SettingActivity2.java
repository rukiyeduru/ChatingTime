package com.example.chatapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity<StrogeReference> extends AppCompatActivity {

    private Button UpdateAccountSettings;
    private EditText userName,userStatus;
    private CircleImageView userProfileImage;

    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference Rootref;

    private static final int GalleryPick = 1;
    private StorageReference userprofilimageref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();
        Rootref=FirebaseDatabase.getInstance().getReference();
        userprofilimageref= FirebaseStorage.getInstance().getReference().child("Profil Images");

        init();
        userName.setVisibility(View.INVISIBLE);

        UpdateAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Updatesetting();
            }
        });

        RetrieveUserInfo();

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntnet = new Intent();
                galleryIntnet.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntnet.setType("image/*");
                startActivityForResult(galleryIntnet,GalleryPick);
            }
        });
    }

    private void RetrieveUserInfo() {
    }

    private void init() {
        UpdateAccountSettings=(Button) findViewById(R.id.update_setting_button);
        userName=(EditText) findViewById(R.id.set_user_name);
        userStatus=(EditText) findViewById(R.id.set_profile_status);
        userProfileImage=(CircleImageView) findViewById(R.id.set_profile_image);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode==GalleryPick && resultCode == RESULT_OK && data!=null))
        {
            Uri ImageUri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode==RESULT_OK){
                Uri resultUri = result.getUri();
                StorageReference filepath= userprofilimageref.child(currentUserID+".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(SettingActivity.this, "Profil Fotoğrafı Güncellendi..", Toast.LENGTH_SHORT).show();
                   }else{
                       String message = task.getException().toString();
                       Toast.makeText(SettingActivity.this, "Hata:"+message, Toast.LENGTH_SHORT).show();
                   }
                    }
                });
            }
        }
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
