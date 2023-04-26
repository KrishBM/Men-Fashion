package com.example.menfashion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

public class CustomerProductAdapter extends RecyclerView.Adapter<CustomerProductAdapter.productViewHolder> {
    List<Product> sProduct;
    String sClothType;
    Context sContext;
    public CustomerProductAdapter(@NonNull List<Product> products,String clothType, Context context) {
        sProduct=products;
        sClothType=clothType;
        sContext=context;

    }

    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_product_choose_card,parent,false);
        return new CustomerProductAdapter.productViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productViewHolder holder, int position) {

        Product product = sProduct.get(position);
        holder.fType.setText(product.getFabricType());
        holder.fColor.setText(product.getFabricColor());
        holder.fPrice.setText(String.format("₹%s", product.getFabricPrice()));

//        Log.d("dataaaaaaaaaaa",product.getFabricType());

        FirebaseStorage.getInstance().getReferenceFromUrl(product.getFabricImage()).getDownloadUrl().addOnSuccessListener(
                uri -> Glide.with(holder.fImg.getContext()).load(uri).into(holder.fImg));

        holder.card.setOnClickListener(v -> {
            //measurement
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            if(sClothType.equals("Shirt")){
                ShirtMeasurementFragment shirtMeasurementFragment=new ShirtMeasurementFragment();
//                shirtMeasurementFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,shirtMeasurementFragment).addToBackStack(null).commit();

            }else {
                TrouserMeasurementFragment trouserMeasurementFragment=new TrouserMeasurementFragment();
//                trouserMeasurementFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,trouserMeasurementFragment).addToBackStack(null).commit();
            }

//            Bundle bundle=new Bundle();
//            FirebaseDatabase.getInstance().getReference().child("ProductData").orderByChild("fabricImage").equalTo(product.getFabricImage()).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    String currentFabricId=snapshot.getKey();
//                    Log.d("currentFabricId",currentFabricId);
//                    bundle.putString("fabricId", currentFabricId);
//                    if(sClothType.equals("Shirt")){
//                        ShirtMeasurementFragment shirtMeasurementFragment=new ShirtMeasurementFragment();
//                        shirtMeasurementFragment.setArguments(bundle);
//                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,shirtMeasurementFragment).addToBackStack(null).commit();
//
//                    }else {
//                        TrouserMeasurementFragment trouserMeasurementFragment=new TrouserMeasurementFragment();
//                        trouserMeasurementFragment.setArguments(bundle);
//                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,trouserMeasurementFragment).addToBackStack(null).commit();
//                    }
//                }//TODO: error
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });

        });
    }

    @Override
    public int getItemCount() {
        return sProduct.size();
    }

    class productViewHolder extends RecyclerView.ViewHolder{

        ImageView fImg;
        TextView fType,fColor,fPrice;
        CardView card;
        public productViewHolder(@NonNull View view) {
            super(view);

            fImg=view.findViewById(R.id.fabricImg);
            fType=view.findViewById(R.id.fType);
            fColor=view.findViewById(R.id.fColor);
            fPrice=view.findViewById(R.id.fPrice);
            card=view.findViewById(R.id.card);
        }
    }
}
