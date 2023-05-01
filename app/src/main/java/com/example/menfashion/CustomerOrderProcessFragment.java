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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CustomerOrderProcessFragment extends Fragment {

    CustomerOrderProcessAdapter customerOrderProcessAdapter;
    RecyclerView recyclerView;
    public CustomerOrderProcessFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_order_process, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((CustomerMainActivity) getActivity()).setToolbarName(": Processing Order");

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataInitialize1();

    }
    private void dataInitialize1() {
        FirebaseDatabase.getInstance().getReference().child("orders").orderByChild("customerID").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Order> orderList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Order order = dataSnapshot.getValue(Order.class);
                    order.setOrderID(dataSnapshot.getKey());

                    /////////
                    // Get the current date
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");

//                    String currentDate=sdf.format(new Date());
                    try {
                        // Parse the delivery date string into a Date object
                        Date deliveryDate = sdf.parse(order.getDeliveryDate());

                        // Get the current date
                        Date currentDate = new Date();

                        // Compare the delivery date with the current date
                        if (currentDate.compareTo(deliveryDate) < 0) {
                            order.setOrderStatus("processing");
                            System.out.println("Delivery date is in the future.");
                            if(order.getOrderStatus().equals("processing") ){//TODO: deliverydate condition
                                orderList.add(order);
                            }
                        } else {
                            order.setOrderStatus("delivered");
                            System.out.println("Delivery date is in the past.");
                        }
                    } catch (Exception e) {
                        System.out.println("An error occurred: " + e.getMessage());
                    }



                }
                recyclerView.setAdapter(new CustomerOrderProcessAdapter(orderList, getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });
    }

}