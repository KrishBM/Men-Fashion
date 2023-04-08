package com.example.menfashion;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);


        bottomNavigationView = findViewById(R.id.nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id=item.getItemId();
                        if(id==R.id.navigation_account){
                            loadFrag(new CustomerAccountFragment(),false);
                        } else if (id==R.id.navigation_order) {
                            loadFrag(new CustomerOrderFragment(),false);
                        } else{//home
                            loadFrag(new CustomerHomeFragment(),true);
                        }
                        return true;
                    }
                });
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    public void loadFrag(Fragment fragment,boolean flag){
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

}