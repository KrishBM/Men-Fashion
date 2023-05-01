package com.example.menfashion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class CustomerOrderFragment extends Fragment {

    CardView requestedCard,processCard,deliverCard;
    AppCompatActivity activity;

    public CustomerOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((CustomerMainActivity) getActivity()).setToolbarName(getResources().getString(R.string.app_name)+": ORDER");

        requestedCard=view.findViewById(R.id.requestedCard);
        processCard=view.findViewById(R.id.processCard);
        deliverCard=view.findViewById(R.id.deliverCard);

        activity = (AppCompatActivity) view.getContext();

        requestedCard.setOnClickListener(v -> {
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,new CustomerOrderRequestedFragment()).addToBackStack(null).commit();
        });

        processCard.setOnClickListener(v -> {
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,new CustomerOrderProcessFragment()).addToBackStack(null).commit();
        });

        deliverCard.setOnClickListener(v -> {
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,new CustomerOrderDeliverFragment()).addToBackStack(null).commit();
        });
    }
}