package com.example.attendancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ViewPager pager;
    TabLayout mTabLayout;
    TabItem first,second;      // dekh login and reg ka fragment banaya h yeh dekh
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);   //androidx toolbar
        setSupportActionBar(toolbar);

        Button btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), scannerCam.class);
                startActivity(i);
            }
        });

      pager=findViewById(R.id.viewpager);
        mTabLayout=findViewById(R.id.tablayout);

       first=findViewById(R.id.firstitem);
       second=findViewById(R.id.seconditem);


        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.nav_view);

        toggle=new ActionBarDrawerToggle(this,drawerLayout, toolbar,R.string.open,R.string.close );

        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        adapter=new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,mTabLayout.getTabCount());
        pager.setAdapter(adapter);


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
              pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent i = null;
        switch (item.getItemId()){
            case R.id.mhome:
                Toast.makeText(this,"Home Button is Clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mAttd:
                i = new Intent(getApplicationContext(), AttendanceTable.class);
                break;
            case R.id.mTable:
                break;
            case R.id.mProf:
                i = new Intent(getApplicationContext(), profilePage.class);
                break;
            case R.id.mSupp:
                i = new Intent(getApplicationContext(), supportPage.class);
                break;
            case R.id.mScan:
                i = new Intent(getApplicationContext(), scannerCam.class);
                break;
        }
        startActivity(i);
        return false;
    }
}