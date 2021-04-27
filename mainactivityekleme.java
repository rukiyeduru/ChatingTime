package com.example.chatapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chatapp1.Fragments.ChatsFragment;
import com.example.chatapp1.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private CircleImageView profile_image;
    private TextView username;

    private TabsAdapter tabsAdapter;
    private ViewPager view_pager;
    private TabLayout tab_layout;
    private Toolbar toolbar;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    FirebaseAuth auth;

    public void init() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chat App");

        view_pager=findViewById(R.id.view_pager);
        tabsAdapter=new TabsAdapter(getSupportFragmentManager());
        view_pager.setAdapter(tabsAdapter);

        tab_layout=findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(view_pager);
        profile_image=findViewById(R.id.profile_image);
        username=findViewById(R.id.username);

        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();

        TabLayout tabLayout=findViewById(R.id.tab_layout);
        ViewPager viewPager=findViewById(R.id.view_pager);

       // ViewPagerAdapter viewPagerAdapter =new ViewPagerAdapter(getSupportFragmentManager());


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    @Override
    protected void onStart() {
        if(firebaseUser==null){
            Intent intneta = new Intent(MainActivity.this,WelcomeActivity.class);
            startActivity(intneta);
            finish();
        }else{
            VerifyUserExistance();
        }
        super.onStart();
    }

    private void VerifyUserExistance() {
        String currentUserId =auth.getCurrentUser().getUid();

        reference.child("Users").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataasnapshot) {
                if(dataasnapshot.child("name").exists()){
                    Toast.makeText(MainActivity.this,"Hoş Geldin!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.logout){
            auth.signOut();
            Intent loginIntent=new Intent(MainActivity.this, WelcomeActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);/*12.video değişiklik*/

            startActivity(loginIntent);
            finish();
        }/*13.videodan eklendi*/
        if(item.getItemId()==R.id.main_setting_option){
            Intent settingIntent=new Intent(MainActivity.this, SettingActivity.class);
            startActivity(settingIntent);
            finish();
            /*16.videodan eklendi*/
        }if (item.getItemId()==R.id.main_create_group_option){
           RequestNewGroup();

        }
        return true;
    }

    private void RequestNewGroup() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this,R.style.AlertDialog);
        builder.setTitle("Grup Adı:");

        final EditText groupNameFiled=new EditText(MainActivity.this);
        groupNameFiled.setHint(" ö. Bilgisayar Mühdensiliği");
        builder.setView(groupNameFiled);

        builder.setPositiveButton("Oluştur", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupName= groupNameFiled.getText().toString();
                if(TextUtils.isEmpty(groupName)){
                    Toast.makeText(MainActivity.this,"Lütfen Grup Adı yazınız..",Toast.LENGTH_SHORT).show();
                }else{
                    CreateNewGroup(groupName);
                }

            }
        });
        builder.setNegativeButton("Geri", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });
        builder.show();
    }

    private void CreateNewGroup(final String groupname) {
        reference.child("Gruplar").child(groupname).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
           if(task.isSuccessful()){
               Toast.makeText(MainActivity.this,groupname +"Grup Oluşturuldu ..",Toast.LENGTH_SHORT).show();
           }
            }
        });
    }

}
