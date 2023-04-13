package com.example.menfashion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class ShirtOrTrouserFragment extends Fragment {

    TextView shopNameTV, shirtChargeTV, trouserChargeTV;
    CardView shirtCard,trouserCard;
//    Bundle bundle;
    AppCompatActivity activity;
    Bundle bundle =new Bundle();
//    Toolbar toolbar;
    String shopName,shirtCharge,trouserCharge,shopAddress;
    CustomerChooseFabricFragment customerChooseFabricFragment;
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
//        toolbar = view.findViewById(R.id.toolbar);
        activity = (AppCompatActivity) view.getContext();
        customerChooseFabricFragment=new CustomerChooseFabricFragment();

        if(getArguments()!=null){
            shopName=getArguments().getString("sname");
            shopAddress=getArguments().getString("address");
            shirtCharge=getArguments().getString("shirtPrice");
            trouserCharge=getArguments().getString("trouserPrice");
            ((CustomerMainActivity) getActivity()).setToolbarName(shopName); //toolbar name change(method is in CustomerMainActivity)

            shirtChargeTV.setText(shirtCharge);
            trouserChargeTV.setText(trouserCharge);
        }

        shirtCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "shirtcard", Toast.LENGTH_SHORT).show();


                bundle.putString("clothType","Shirt");
                customerChooseFabricFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,customerChooseFabricFragment).addToBackStack(null).commit();

            }
        });

        trouserCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("clothType","Trouser");
                customerChooseFabricFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,customerChooseFabricFragment).addToBackStack(null).commit();

            }
        });
    }
}