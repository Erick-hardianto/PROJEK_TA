package com.example.erick.ta_projek_erick;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class menu_utama extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Toolbar toolbar =  findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new homefragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_kelolakamar:
                    fragment = new lihatdatakamar();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_reservasi:
                    fragment = new lihatdatareservasi();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);



        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // load the store fragment by default
        toolbar.setSubtitle("EKSARASA HOTEL");
        loadFragment(new homefragment());


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}