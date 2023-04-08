package com.example.menfashion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class ShirtOrTrouserFragment extends Fragment {

    TextView shopNameTV, shirtChargeTV, trouserChargeTV;
    CardView shirtCard,trouserCard;
    Toolbar toolbar;
    String shopName,shirtCharge,trouserCharge,shopAddress;

    public ShirtOrTrouserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shirt_or_trouser, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        shopNameTV =view.findViewById(R.id.shop_name);
        shirtChargeTV =view.findViewById(R.id.currentShirtCharge);
        trouserChargeTV =view.findViewById(R.id.currentTrouserCharge);
        shirtCard=view.findViewById(R.id.ShirtCard);
        trouserCard=view.findViewById(R.id.TrouserCard);
        toolbar = view.findViewById(R.id.toolbar);

        if(getArguments()!=null){
            shopName=getArguments().getString("sname");
            shopAddress=getArguments().getString("address");
            shirtCharge=getArguments().getString("shirtPrice");
            trouserCharge=getArguments().getString("trouserPrice");
//            shopNameTV.setText(shopName);
            ///////////////TODO
            if(toolbar!=null) {
                toolbar.setTitle(shopName);
            }
            //////////////////
            shirtChargeTV.setText(shirtCharge);
            trouserChargeTV.setText(trouserCharge);
        }


//        if (getIntent().hasExtra("shop_data")){
//            ArrayList<String> shop_data=getIntent().getExtras().getStringArrayList("shop_data");
//            shopName= shop_data.get(0);
//            shopAddress= shop_data.get(1);
//            shirtCharge= shop_data.get(2);
//            trouserCharge=shop_data.get(3);
//
//            shopNameTV.setText(shopName);
//            shirtChargeTV.setText(shirtCharge);
//            trouserChargeTV.setText(trouserCharge);
//        }

        shirtCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "shirtcard", Toast.LENGTH_SHORT).show();
            }
        });

        trouserCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"trousercard",Toast.LENGTH_SHORT).show();
            }
        });
    }
}