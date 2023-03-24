package com.example.menfashion;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    private RelativeLayout rL_Splash;
    String mStatus = "null";


    // fetch maintain
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("maintain/status");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rL_Splash = findViewById(R.id.rL_Splash);


        //get maintain Data...
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mStatus = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError e) {
                Toast.makeText(SplashActivity.this, "" + e.toException(), Toast.LENGTH_SHORT).show();
            }
        });


        mTask();


    }

    private void mTask() {

        if (isNetworkConnected()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
// below true indicates that application is now in maintenance............
                    if (mStatus.equals("true")) {
                        startActivity(new Intent(SplashActivity.this, AppMaintainActivity.class));
                    } else {

                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if (currentUser != null) {
                                    String uid = currentUser.getUid();
                                    Log.e("@data", "splash: current user is: " + uid);

                                    DatabaseReference userRef = database.getReference().child("users").child(uid);
                                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                String userRole = snapshot.child("role").getValue(String.class);

                                                assert userRole != null;
                                                if(userRole.equals("tailor")){
                                                    startActivity(new Intent(SplashActivity.this, TailorMainActivity.class));
                                                }
                                                if(userRole.equals("customer")){
                                                    startActivity(new Intent(SplashActivity.this, CustomerMainActivity.class));
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            // Handle the error
                                        }
                                    });


//                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));//customer/tailor route

                                } else {
                                    Log.e("@data", "splash: current user is @null");
                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                }
                                finish();
                            }
                        }, 2000);
                    }


                }
            }, 3000);
        } else {
            Snackbar.make(rL_Splash, "check your internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTask();
                        }
                    }).show();
        }

    }


    @Override
    public void onBackPressed() {
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}