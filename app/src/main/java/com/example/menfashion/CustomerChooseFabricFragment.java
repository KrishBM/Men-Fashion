package com.example.menfashion;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerChooseFabricFragment extends Fragment {

    RecyclerView recyclerView;
    String clothType,shopName,shopId,tailorCharge,currentTailorID;
    public CustomerChooseFabricFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_choose_fabric, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments()!=null) {
            clothType = getArguments().getString("clothType");
            shopName=getArguments().getString("shopName");
            shopId=getArguments().getString("sID");
            tailorCharge=getArguments().getString("tailorCharge");

            ((CustomerMainActivity) getActivity()).setToolbarName(shopName+": "+clothType);
//            Log.d("clothTypeeeeeeeeeeeeeeeee1",clothType);
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //for currentTailorID
        FirebaseDatabase.getInstance().getReference().child("users").orderByChild("ShopID").equalTo(shopId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    currentTailorID=dataSnapshot.getKey();
//                    Log.d("tailorIDDDDDDDDDDD",currentTailorID);
                }
                if(!currentTailorID.isEmpty()){
                    dataInitialize1();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase(tailorID)", "Error: " + error.getMessage());
            }
        });


    }

    private void dataInitialize1() {
        FirebaseDatabase.getInstance().getReference().child("ProductData").orderByChild("currentTailorID").equalTo(currentTailorID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    product.setId(dataSnapshot.getKey());
                    if(product.getClothType().equals(clothType)){
                        productList.add(product);
                    }
                }
                recyclerView.setAdapter(new CustomerProductAdapter(productList,clothType,shopId,tailorCharge, getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });
    }

}