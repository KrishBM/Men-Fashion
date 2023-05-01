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

public class TailorOrderProcessingAdapter extends RecyclerView.Adapter<TailorOrderProcessingAdapter.tailorProcessingViewHolder>{
    List<Order> orders;

    Context context;

    public TailorOrderProcessingAdapter(@NonNull List<Order> orders, Context context) {
        this.orders=orders;
        this.context=context;

    }

    public TailorOrderProcessingAdapter.tailorProcessingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tailor_processing_delivered_order,parent,false);
        return new TailorOrderProcessingAdapter.tailorProcessingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tailorProcessingViewHolder holder, int position) {

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
        holder.deliveredDate.setText(order.deliveryDate);

        FirebaseDatabase.getInstance().getReference().child("users").child(order.getCustomerID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    String customerName = user.getName();
                    holder.customerName.setText(WordUtils.capitalizeFully(customerName));
                    String customerMobileNo = user.getNumber();
                    holder.phoneNumber.setText(customerMobileNo);
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

    class tailorProcessingViewHolder extends RecyclerView.ViewHolder{

        ImageView orderTypeImg;
        TextView customerName,fabricType,fabricColor,amount,issuedDate,deliveredDate,phoneNumber;

        CardView card;
        public tailorProcessingViewHolder(@NonNull View view) {
            super(view);

            orderTypeImg=view.findViewById(R.id.orderTypeImg);
            customerName=view.findViewById(R.id.customerName);
            fabricType=view.findViewById(R.id.fabricType);
            fabricColor=view.findViewById(R.id.fabricColor);
            amount=view.findViewById(R.id.amount);
            issuedDate=view.findViewById(R.id.issuedDate);
            deliveredDate=view.findViewById(R.id.deliveredDate);
            phoneNumber=view.findViewById(R.id.phoneNumber);
            card=view.findViewById(R.id.card);
        }
    }
}
