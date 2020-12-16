package com.example.chatingtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;

import com.google.firebase.auth.FirebaseUser;

public class TabActivity extends AppCompatActivity {
    private Toolbar actionbar;
    private ViewPager vpMain;
    private TableLayout tabsMain;
    private TabsAdapter tabsAdapter;

    private FirebaseUser currentUser;


    public void init(){
        actionbar=(Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(actionbar);
        getSupportActionBar().setTitle(R.string.app_name);

        vpMain=(ViewPager) findViewById(R.id.vpMain);
        tabsAdapter=new TabsAdapter(getSupportFragmentManager());
        vpMain.setAdapter(tabsAdapter);

        tabsMain=(TableLayout) findViewById(R.id.tabsMain);
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
            Intent welcomeIntent = new Intent(TabActivity.this,MainActivity.class);
            startActivity(welcomeIntent);
            finish();
        }
        super.onStart();
    }
}
