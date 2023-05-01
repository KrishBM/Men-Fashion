package com.example.menfashion;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.Calendar;
import java.util.List;

public class TailorOrderRequestedAdapter extends RecyclerView.Adapter<TailorOrderRequestedAdapter.tailorRequestViewHolder>{
    List<Order> orders;

    Context context;

    public TailorOrderRequestedAdapter(@NonNull List<Order> orders, Context context) {
        this.orders=orders;
        this.context=context;

    }

    public TailorOrderRequestedAdapter.tailorRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tailor_requested_order,parent,false);
        return new TailorOrderRequestedAdapter.tailorRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tailorRequestViewHolder holder, int position) {

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
        holder.button_dateP.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();

            // on below line we are getting
            // our day, month and year.
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // on below line we are creating a variable for date picker dialog.
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year1, monthOfYear, dayOfMonth) -> {
                // on below line we are setting date to our text view.
                holder.deliveredDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);
                holder.submit.setOnClickListener(v1 -> {
                    FirebaseDatabase.getInstance().getReference().child("orders").child(order.getOrderID()).child("deliveryDate").setValue(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);
                    FirebaseDatabase.getInstance().getReference().child("orders").child(order.getOrderID()).child("orderStatus").setValue("processing");
                    notifyDataSetChanged();//TODO: Is not working right now....
                });

            },
                    // on below line we are passing year,
                    // month and day for selected date in our date picker.
                    year, month, day);

            // Set the minimum date to the current date
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            // at last we are calling show to
            // display our date picker dialog.
            datePickerDialog.show();
        });



    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

    class tailorRequestViewHolder extends RecyclerView.ViewHolder{

        ImageView orderTypeImg,button_dateP;
        Button submit;
        TextView customerName,fabricType,fabricColor,amount,issuedDate,deliveredDate,phoneNumber;

        CardView card;
        public tailorRequestViewHolder(@NonNull View view) {
            super(view);

            orderTypeImg=view.findViewById(R.id.orderTypeImg);
            button_dateP=view.findViewById(R.id.button_dateP);
            customerName=view.findViewById(R.id.customerName);
            fabricType=view.findViewById(R.id.fabricType);
            fabricColor=view.findViewById(R.id.fabricColor);
            amount=view.findViewById(R.id.amount);
            issuedDate=view.findViewById(R.id.issuedDate);
            deliveredDate=view.findViewById(R.id.deliveredDate);
            phoneNumber=view.findViewById(R.id.phoneNumber);
            submit=view.findViewById(R.id.submit);
            card=view.findViewById(R.id.card);
        }
    }
}
