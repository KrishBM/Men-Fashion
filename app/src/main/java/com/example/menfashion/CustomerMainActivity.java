package com.example.menfashion;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                startActivity(new Intent(getApplicationContext(), CustomerHomeActivity.class));
                                return true;
                            case R.id.navigation_order:
                                startActivity(new Intent(getApplicationContext(), CustomerOrderActivity.class));
                                return true;
                            case R.id.navigation_account:
                                startActivity(new Intent(getApplicationContext(), CustomerAccountActivity.class));
                                return true;
                        }
                        return false;
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        startActivity(new Intent(getApplicationContext(),CustomerHomeActivity.class));
    }
}