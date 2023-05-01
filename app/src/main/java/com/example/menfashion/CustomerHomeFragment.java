package com.example.menfashion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerHomeFragment extends Fragment {

    RecyclerView recyclerView;
    ShopAdapter shopAdapter;

    public CustomerHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((CustomerMainActivity) getActivity()).setToolbarName(getResources().getString(R.string.app_name));

        FirebaseRecyclerOptions<Shop> options=dataInitialize();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        shopAdapter=new ShopAdapter(options,getContext());
        recyclerView.setAdapter(shopAdapter);

    }

    private FirebaseRecyclerOptions<Shop> dataInitialize() {
        return new FirebaseRecyclerOptions.Builder<Shop>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("ShopData"), snapshot -> {
                    Shop shop=snapshot.getValue(Shop.class);
                    shop.setsID(snapshot.getKey());
                    return shop;
                })
                .build();
    }


    @Override
    public void onStart() {
        super.onStart();
        shopAdapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        shopAdapter.stopListening();
    }


}