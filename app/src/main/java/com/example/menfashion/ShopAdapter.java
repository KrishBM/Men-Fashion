package com.example.menfashion;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ShopAdapter extends FirebaseRecyclerAdapter<Shop,ShopAdapter.shopViewHolder> {

    Context sContext;
    public ShopAdapter(@NonNull FirebaseRecyclerOptions<Shop> options, Context context) {
        super(options);
        sContext=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull shopViewHolder holder, int position, @NonNull Shop shop) {
        holder.sName.setText(shop.getSname());
        holder.cShirt.setText(String.format("₹%s", shop.getShirtPrice()));
        holder.cTrouser.setText(String.format("₹%s", shop.getTrouserPrice()));//TODO
        FirebaseStorage.getInstance().getReferenceFromUrl(shop.getLogo()).getDownloadUrl().addOnSuccessListener(
                uri -> Glide.with(holder.logo.getContext()).load(uri).into(holder.logo));

        holder.card.setOnClickListener(v -> {
            Intent intent1 = new Intent(sContext,ShirtOrTrouser.class);

            ArrayList<String> shop_data=new ArrayList<String>();
            shop_data.add(shop.sname);
            shop_data.add(shop.address);
            shop_data.add(shop.shirtPrice);
            shop_data.add(shop.trouserPrice);

            intent1.putStringArrayListExtra("shop_data", shop_data);
            sContext.startActivity(intent1);

        });
    }

    @NonNull
    @Override
    public shopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_card,parent,false);
        return new shopViewHolder(view);
    }

    class shopViewHolder extends RecyclerView.ViewHolder{

        ImageView logo;
        TextView sName,cShirt,cTrouser;
        CardView card;
        public shopViewHolder(@NonNull View view) {
            super(view);

            logo=view.findViewById(R.id.shopLogo);
            sName=view.findViewById(R.id.shopName);
            cShirt=view.findViewById(R.id.shirtCharge);
            cTrouser=view.findViewById(R.id.trouserCharge);
            card = view.findViewById(R.id.card);
        }
    }

}

