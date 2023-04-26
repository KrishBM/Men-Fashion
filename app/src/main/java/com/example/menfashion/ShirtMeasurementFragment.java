package com.example.menfashion;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShirtMeasurementFragment extends Fragment {

    EditText chestET, waistET, bottomHemET, frontLenET, sleeveLenET, sleeveWidET, centerBackET, shoulderWidET, cuffET, collarSizeET;
    Button submitShirtMeasurement;
    String chest, waist, bottomHem, frontLen, sleeveLen, sleeveWid, centerBack, shoulderWid, cuff, collarSize;
    String clothType="Shirt",currentCustomerID;
    DatabaseReference measurementRef=FirebaseDatabase.getInstance().getReference().child("Measurements");
    public ShirtMeasurementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shirt_measurement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chestET=view.findViewById(R.id.chestET);
        waistET=view.findViewById(R.id.waistET);
        bottomHemET=view.findViewById(R.id.bottomHemET);
        frontLenET=view.findViewById(R.id.frontLengthET);
        sleeveLenET=view.findViewById(R.id.sleeveLengthET);
        sleeveWidET=view.findViewById(R.id.sleeveWidthET);
        centerBackET=view.findViewById(R.id.centerBackLengthET);
        shoulderWidET=view.findViewById(R.id.shoulderWidthET);
        cuffET=view.findViewById(R.id.cuffCircumferenceET);
        collarSizeET=view.findViewById(R.id.collarSizeET);

        submitShirtMeasurement=view.findViewById(R.id.submitShirtMeasurement);

        currentCustomerID= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        measurementRef.orderByChild("currentCustomerID").equalTo(currentCustomerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ShirtMeasurement> measurements = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ShirtMeasurement shirtMeasurement = dataSnapshot.getValue(ShirtMeasurement.class);
                    shirtMeasurement.setId(dataSnapshot.getKey());
                    if(shirtMeasurement.getClothType().equals(clothType)){
                        measurements.add(shirtMeasurement);
                    }
                }
                if(measurements.isEmpty()){

                    newShirtDataAdd(measurementRef.push().getKey());
                }else {
                    oldShirtDataFetch(measurements);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase(tailorID)", "Error: " + error.getMessage());
            }
        });



    }
    public void newShirtDataAdd(String id){
        submitShirtMeasurement.setOnClickListener(v->{
            if(TextUtils.isEmpty(chestET.getText()) || TextUtils.isEmpty(waistET.getText()) || TextUtils.isEmpty(bottomHemET.getText()) || TextUtils.isEmpty(frontLenET.getText()) || TextUtils.isEmpty(sleeveLenET.getText()) || TextUtils.isEmpty(sleeveWidET.getText()) || TextUtils.isEmpty(centerBackET.getText()) || TextUtils.isEmpty(shoulderWidET.getText()) || TextUtils.isEmpty(cuffET.getText()) || TextUtils.isEmpty(collarSizeET.getText())){
                Toast.makeText(getContext(), "Fill All The Details...!!", Toast.LENGTH_SHORT).show();
            } else {
                chest=chestET.getText().toString();
                waist=waistET.getText().toString();
                bottomHem=bottomHemET.getText().toString();
                frontLen=frontLenET.getText().toString();
                sleeveLen=sleeveLenET.getText().toString();
                sleeveWid=sleeveWidET.getText().toString();
                centerBack=centerBackET.getText().toString();
                shoulderWid=shoulderWidET.getText().toString();
                cuff=cuffET.getText().toString();
                collarSize=collarSizeET.getText().toString();

                ShirtMeasurement shirtMeasurement=new ShirtMeasurement(clothType, currentCustomerID, chest, waist, bottomHem, frontLen, sleeveLen, sleeveWid, centerBack, shoulderWid, cuff, collarSize);

                measurementRef.child(id).setValue(shirtMeasurement).addOnSuccessListener(unused -> {
                    Toast.makeText(getContext(), "data added", Toast.LENGTH_SHORT).show();
                });
                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("measurementShirtID").setValue(id);
            }
        });
    }

    public void oldShirtDataFetch(List<ShirtMeasurement> measurements){
        chestET.setText(measurements.get(0).chest);
        waistET.setText(measurements.get(0).waist);
        bottomHemET.setText(measurements.get(0).bottomHem);
        frontLenET.setText(measurements.get(0).frontLen);
        sleeveLenET.setText(measurements.get(0).sleeveLen);
        sleeveWidET.setText(measurements.get(0).sleeveWid);
        centerBackET.setText(measurements.get(0).centerBack);
        shoulderWidET.setText(measurements.get(0).shoulderWid);
        cuffET.setText(measurements.get(0).cuff);
        collarSizeET.setText(measurements.get(0).collarSize);

        newShirtDataAdd(measurements.get(0).id);
    }
}