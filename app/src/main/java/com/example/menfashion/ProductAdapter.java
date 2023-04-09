package com.example.menfashion;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class ProductAdapter extends FirebaseRecyclerAdapter<Product,ProductAdapter.productViewHolder> {
    Context sContext;
    public ProductAdapter(@NonNull FirebaseRecyclerOptions<Product> options, Context context) {
        super(options);
        sContext=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull ProductAdapter.productViewHolder holder, int position, @NonNull Product product) {
        holder.fType.setText(product.getFabricType());
        holder.fColor.setText(product.getFabricColor());
        holder.fPrice.setText(String.format("₹%s", product.getFabricPrice()));
        if(product.getFabricAvailable().equals("On")){
            holder.Aswitch.setChecked(true);
        }else {
            holder.Aswitch.setChecked(false);
        }

        FirebaseStorage.getInstance().getReferenceFromUrl(product.getFabricImage()).getDownloadUrl().addOnSuccessListener(
                uri -> Glide.with(holder.fImg.getContext()).load(uri).into(holder.fImg));

        holder.deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                user.setText(product.getFabricColor());

                del_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                del_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //delete data....
                        DatabaseReference productDatabaseReference = FirebaseDatabase.getInstance().getReference().child("ProductData");
                        productDatabaseReference.child(getRef(position).getKey()).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialog.dismiss();
                                        Toast.makeText(sContext, "delete successful!", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        notifyDataSetChanged();
                                        Toast.makeText(sContext, "error! data not deleted!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }
        });

        holder.Aswitch.setOnClickListener(v -> {
            DatabaseReference productDatabaseReference = FirebaseDatabase.getInstance().getReference().child("ProductData");
            if (holder.Aswitch.isChecked())
                productDatabaseReference.child(getRef(position).getKey()).child("fabricAvailable").setValue(holder.Aswitch.getTextOn().toString());
            else
                productDatabaseReference.child(getRef(position).getKey()).child("fabricAvailable").setValue(holder.Aswitch.getTextOff().toString());
        });
    }
    public ProductAdapter.productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card,parent,false);
        return new ProductAdapter.productViewHolder(view);
    }

    class productViewHolder extends RecyclerView.ViewHolder{

        ImageView fImg,deleteB;
        TextView fType,fColor,fPrice;
        Switch Aswitch;
        public productViewHolder(@NonNull View view) {
            super(view);

            fImg=view.findViewById(R.id.fabricImg);
            deleteB=view.findViewById(R.id.button_delete);
            fType=view.findViewById(R.id.fType);
            fColor=view.findViewById(R.id.fColor);
            fPrice=view.findViewById(R.id.fPrice);
            Aswitch=view.findViewById(R.id.availableSwitch);
        }
    }
}
