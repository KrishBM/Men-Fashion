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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;


public class TailorProductFragment extends Fragment {

    FloatingActionButton addP;
    RecyclerView recyclerView;
    TailorProductAdapter tailorProductAdapter;

    public TailorProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tailor_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addP=view.findViewById(R.id.addP);
        addP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProductDialog addProductDialog=new AddProductDialog();
                addProductDialog.show(getFragmentManager(),"example dialog");
            }
        });

        FirebaseRecyclerOptions<Product> options=dataInitialize();

        recyclerView = view.findViewById(R.id.recyclerViewProduct);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tailorProductAdapter =new TailorProductAdapter(options,getContext());
        recyclerView.setAdapter(tailorProductAdapter);
    }
    private FirebaseRecyclerOptions<Product> dataInitialize() {
        return new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("ProductData"), Product.class)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        tailorProductAdapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        tailorProductAdapter.stopListening();
    }

}