package com.example.menfashion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.text.WordUtils;

import java.util.List;

public class CustomerOrderProcessAdapter extends RecyclerView.Adapter<CustomerOrderProcessAdapter.customerProcessingViewHolder>{
    List<Order> orders;

    Context context;

    public CustomerOrderProcessAdapter(@NonNull List<Order> orders, Context context) {
        this.orders=orders;
        this.context=context;

    }

    public customerProcessingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_processing_delivered_order,parent,false);
        return new customerProcessingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull customerProcessingViewHolder holder, int position) {

        Order order = orders.get(position);

        if(order.getClothType().equals("Shirt")){
            holder.orderTypeImg.setImageResource(R.drawable.shirt);
        }else{
            holder.orderTypeImg.setImageResource(R.drawable.trouser);
        }
        try {
            holder.amount.setText(String.format("₹%s", Integer.parseInt(order.fabricPrice) + Integer.parseInt(order.tailorCharge)));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Cannot convert string to int.");
            e.printStackTrace();
        }
        holder.issuedDate.setText(order.orderedDate.substring(0,10));
        holder.DeliveredOn.setText(order.deliveryDate);

        FirebaseDatabase.getInstance().getReference().child("ShopData").child(order.getShopId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Shop shop = snapshot.getValue(Shop.class);
                if (shop != null) {
                    String shopName = shop.getSname();
                    holder.shopName.setText(WordUtils.capitalizeFully(shopName));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase(tailorID)", "Error: " + error.getMessage());
            }
        });

        final String[] fcolor = new String[1];
        final String[] ftype = new String[1];

        FirebaseDatabase.getInstance().getReference().child("ProductData").child(order.getProductID()).addListenerForSingleValueEvent(new ValueEventListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Product product = snapshot.getValue(Product.class);

                fcolor[0] =WordUtils.capitalizeFully(product.getFabricColor());
                ftype[0] =WordUtils.capitalizeFully(product.getFabricType());
                holder.fabricColor.setText(fcolor[0]);
                holder.fabricType.setText(ftype[0]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase(tailorID)", "Error: " + error.getMessage());
            }
        });

    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

    class customerProcessingViewHolder extends RecyclerView.ViewHolder{

        ImageView orderTypeImg;
        TextView shopName,fabricType,fabricColor,amount,issuedDate,DeliveredOn;

        CardView card;
        public customerProcessingViewHolder(@NonNull View view) {
            super(view);

            orderTypeImg=view.findViewById(R.id.orderTypeImg);
            DeliveredOn=view.findViewById(R.id.DeliveredOn);
            shopName=view.findViewById(R.id.shopName);
            fabricType=view.findViewById(R.id.fabricType);
            fabricColor=view.findViewById(R.id.fabricColor);
            amount=view.findViewById(R.id.amount);
            issuedDate=view.findViewById(R.id.issuedDate);
            card=view.findViewById(R.id.card);
        }
    }
}
