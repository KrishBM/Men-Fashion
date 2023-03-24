package com.example.menfashion;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class ShirtOrTrouser extends AppCompatActivity {
    TextView shopNameTV, shirtChargeTV, trouserChargeTV;
    CardView shirtCard,trouserCard;
    String shopName,shirtCharge,trouserCharge,shopAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shirt_or_trouser);

        shopNameTV =findViewById(R.id.shop_name);
        shirtChargeTV =findViewById(R.id.currentShirtCharge);
        trouserChargeTV =findViewById(R.id.currentTrouserCharge);
        shirtCard=findViewById(R.id.ShirtCard);
        trouserCard=findViewById(R.id.TrouserCard);

        if (getIntent().hasExtra("shop_data")){
            ArrayList<String> shop_data=getIntent().getExtras().getStringArrayList("shop_data");
            shopName= shop_data.get(0);
            shopAddress= shop_data.get(1);
            shirtCharge= shop_data.get(2);
            trouserCharge=shop_data.get(3);

            shopNameTV.setText(shopName);
            shirtChargeTV.setText(shirtCharge);
            trouserChargeTV.setText(trouserCharge);
        }

        shirtCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShirtOrTrouser.this, "shirtcard", Toast.LENGTH_SHORT).show();
            }
        });

        trouserCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShirtOrTrouser.this,"trousercard",Toast.LENGTH_SHORT).show();
            }
        });

    }
}