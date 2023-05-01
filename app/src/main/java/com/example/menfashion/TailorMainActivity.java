package com.example.menfashion;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TailorMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_main);

        bottomNavigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id=item.getItemId();
                        if(id==R.id.navigation_account){
                            loadFrag(new TailorAccountFragment(),false);
                        } else if (id==R.id.navigation_product) {
                            loadFrag(new TailorProductFragment(),false);
                        } else{//home
                            loadFrag(new TailorHomeFragment(),true);
                        }
                        return true;
                    }
                });
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }
    public void loadFrag(Fragment fragment, boolean flag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        if(flag){
            fragmentTransaction.add(R.id.container,fragment);
            fragmentManager.popBackStack("root_fragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.addToBackStack("root_fragment");
        }else {

            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
    public void setToolbarName(String name){
        if(toolbar!=null) {
            toolbar.setTitle(name);
        }
    }
}