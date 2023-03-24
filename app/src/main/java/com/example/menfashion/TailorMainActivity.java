package com.example.menfashion;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TailorMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_main);

        bottomNavigationView = findViewById(R.id.nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                startActivity(new Intent(getApplicationContext(), TailorHomeActivity.class));
                                return true;
                            case R.id.navigation_product:
                                startActivity(new Intent(getApplicationContext(), TailorProductActivity.class));
                                return true;
                            case R.id.navigation_account:
                                startActivity(new Intent(getApplicationContext(), TailorAccountActivity.class));
                                return true;
                        }
                        return false;
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        startActivity(new Intent(getApplicationContext(),TailorHomeActivity.class));
    }
}