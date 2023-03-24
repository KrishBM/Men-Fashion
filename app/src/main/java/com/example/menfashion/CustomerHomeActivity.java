package com.example.menfashion;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerHomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShopAdapter shopAdapter;
    private RelativeLayout rl;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);


        rl = findViewById(R.id.rl);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Shop> options =
                new FirebaseRecyclerOptions.Builder<Shop>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("ShopData"), Shop.class)//TODO
                        .build();

        shopAdapter=new ShopAdapter(options,this);
        recyclerView.setAdapter(shopAdapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        shopAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        shopAdapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            this.finishAffinity();
        }
        this.doubleBackToExitPressedOnce = true;

        Snackbar.make(rl, "Please click BACK again to exit", Snackbar.LENGTH_SHORT)
                .setAction("EXIT", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishAffinity();
                    }
                }).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}