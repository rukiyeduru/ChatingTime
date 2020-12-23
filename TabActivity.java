package com.example.chatingtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TabActivity extends AppCompatActivity {
    private Toolbar actionbar;
    private ViewPager vpMain;
    private TabItem tabsMain;
    private TabsAdapter tabsAdapter;

    private FirebaseUser currentUser;
    private  FirebaseAuth auth;


    public void init(){
        actionbar=(Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(actionbar);
        getSupportActionBar().setTitle(R.string.app_name);

        auth= FirebaseAuth.getInstance();
        currentUser= auth.getCurrentUser();
        

        vpMain=(ViewPager) findViewById(R.id.vpMain);
        tabsAdapter=new TabsAdapter(getSupportFragmentManager());
        vpMain.setAdapter(tabsAdapter);
        tabsMain=(TabItem) findViewById(R.id.tabsMain);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        init();
    }
    @Override
    protected void onStart() {
        if(currentUser==null){
            Intent mainIntent = new Intent(TabActivity.this,MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
        super.onStart();
    }
    @Override/*menümüzü ouşturduk*/
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override/*menüden eleman seçildiğinde bu metod çağırılacak mesela çıkış yap dendiğinde*/
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.mainLogout){/*eğer id miz çıkış yap id sine eşit ise*/

            auth.signOut();/*oturumu kapatması için komut verdık*/
            Intent loginIntent =new Intent(TabActivity.this,LoginActivity.class);
            startActivity(loginIntent);
            finish();


        }
        return true;
    }
}
