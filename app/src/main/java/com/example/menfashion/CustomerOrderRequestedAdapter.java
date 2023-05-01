package com.example.menfashion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.text.WordUtils;

import java.util.List;

public class CustomerOrderRequestedAdapter extends RecyclerView.Adapter<CustomerOrderRequestedAdapter.customerRequestViewHolder>{
    List<Order> orders;

    Context context;

    public CustomerOrderRequestedAdapter(@NonNull List<Order> orders, Context context) {
        this.orders=orders;
        this.context=context;

    }

    public CustomerOrderRequestedAdapter.customerRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_requested_order,parent,false);
        return new CustomerOrderRequestedAdapter.customerRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull customerRequestViewHolder holder, int position) {

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
        holder.button_delete.setOnClickListener(v -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(v.getContext());
            LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.dialog_delete, null);
            dialogBuilder.setView(dialogView);
            dialogBuilder.setCancelable(false);
            final AlertDialog dialog = dialogBuilder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            TextView del_cancel = dialog.findViewById(R.id.del_cancel);
            TextView del_delete = dialog.findViewById(R.id.del_delete);
            TextView user = dialog.findViewById(R.id.user);

            user.setText(order.getClothType()+" "+fcolor[0]+" "+ftype[0]);

            del_cancel.setOnClickListener(v1 -> dialog.dismiss());

            del_delete.setOnClickListener(v12 -> {

                FirebaseDatabase.getInstance().getReference().child("ShopData").child(order.getShopId()).child("orderIDs").orderByValue().equalTo(order.getOrderID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            FirebaseDatabase.getInstance().getReference().child("ShopData").child(order.getShopId()).child("orderIDs").child(dataSnapshot.getKey()).removeValue()
                                    .addOnSuccessListener(aVoid -> {
                                        notifyDataSetChanged();
                                    }).addOnFailureListener(e -> {
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "error! data not deleted!", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                //delete data....
                DatabaseReference orderDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders");
                orderDatabaseReference.child(order.getOrderID()).removeValue()
                        .addOnSuccessListener(aVoid -> {
                            dialog.dismiss();
                            Toast.makeText(context, "delete successful!", Toast.LENGTH_SHORT).show();
                            orders.remove(position);
                            notifyDataSetChanged();
                        }).addOnFailureListener(e -> {
                            notifyDataSetChanged();
                            Toast.makeText(context, "error! data not deleted!", Toast.LENGTH_SHORT).show();
                        });


            });
        });


    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

    class customerRequestViewHolder extends RecyclerView.ViewHolder{

        ImageView orderTypeImg,button_delete;
        TextView shopName,fabricType,fabricColor,amount,issuedDate;

        CardView card;
        public customerRequestViewHolder(@NonNull View view) {
            super(view);

            orderTypeImg=view.findViewById(R.id.orderTypeImg);
            button_delete=view.findViewById(R.id.button_delete);
            shopName=view.findViewById(R.id.shopName);
            fabricType=view.findViewById(R.id.fabricType);
            fabricColor=view.findViewById(R.id.fabricColor);
            amount=view.findViewById(R.id.amount);
            issuedDate=view.findViewById(R.id.issuedDate);
            card=view.findViewById(R.id.card);
        }
    }
}
