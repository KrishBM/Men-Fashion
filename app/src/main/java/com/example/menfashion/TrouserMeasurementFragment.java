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

public class TrouserMeasurementFragment extends Fragment {

    EditText waistBeltET, frontRiseET, inseamET, outseamET, seatET, thighET, kneeET, heapET, backRiseET, hemET;
    String waistBelt, frontRise, inseam, outseam, seat, thigh, knee, heap, backRise, hem;
    String clothType="Trouser",currentCustomerID;
    DatabaseReference measurementRef=FirebaseDatabase.getInstance().getReference().child("Measurements");
    Button submitTrouserMeasurement;
    public TrouserMeasurementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trouser_measurement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        waistBeltET=view.findViewById(R.id.waistBeltET);
        frontRiseET=view.findViewById(R.id.frontRiseET);
        inseamET=view.findViewById(R.id.inseamET);
        outseamET=view.findViewById(R.id.outseamET);
        seatET=view.findViewById(R.id.seatET);
        thighET=view.findViewById(R.id.thighET);
        kneeET=view.findViewById(R.id.kneeET);
        heapET=view.findViewById(R.id.heapET);
        backRiseET=view.findViewById(R.id.backRiseET);
        hemET=view.findViewById(R.id.hemET);

        submitTrouserMeasurement=view.findViewById(R.id.submitTrouserMeasurement);

        currentCustomerID= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        measurementRef.orderByChild("currentCustomerID").equalTo(currentCustomerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<TrouserMeasurement> measurements = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TrouserMeasurement trouserMeasurement = dataSnapshot.getValue(TrouserMeasurement.class);
                    trouserMeasurement.setId(dataSnapshot.getKey());
                    if(trouserMeasurement.getClothType().equals(clothType)){
                        measurements.add(trouserMeasurement);
                    }
                }
                if(measurements.isEmpty()){

                    newTrouserDataAdd(measurementRef.push().getKey());
                }else {
                    oldTrouserDataFetch(measurements);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase(tailorID)", "Error: " + error.getMessage());
            }
        });
    }
    private void newTrouserDataAdd(String id){
        submitTrouserMeasurement.setOnClickListener(v->{
            if(TextUtils.isEmpty(waistBeltET.getText()) || TextUtils.isEmpty(frontRiseET.getText()) || TextUtils.isEmpty(inseamET.getText()) || TextUtils.isEmpty(outseamET.getText()) || TextUtils.isEmpty(seatET.getText()) || TextUtils.isEmpty(thighET.getText()) || TextUtils.isEmpty(kneeET.getText()) || TextUtils.isEmpty(heapET.getText()) || TextUtils.isEmpty(backRiseET.getText()) || TextUtils.isEmpty(hemET.getText())){
                Toast.makeText(getContext(), "Fill All The Details...!!", Toast.LENGTH_SHORT).show();
            } else {
                waistBelt=waistBeltET.getText().toString();
                frontRise=frontRiseET.getText().toString();
                inseam=inseamET.getText().toString();
                outseam=outseamET.getText().toString();
                seat=seatET.getText().toString();
                thigh=thighET.getText().toString();
                knee=kneeET.getText().toString();
                heap=heapET.getText().toString();
                backRise=backRiseET.getText().toString();
                hem=hemET.getText().toString();

                TrouserMeasurement trouserMeasurement=new TrouserMeasurement(clothType, currentCustomerID, waistBelt, frontRise, inseam, outseam, seat, thigh, knee, heap, backRise, hem);

                measurementRef.child(id).setValue(trouserMeasurement).addOnSuccessListener(unused -> {
                    Toast.makeText(getContext(), "data added", Toast.LENGTH_SHORT).show();
                });
                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("measurementTrouserID").setValue(id);
            }
        });
    }

    private void oldTrouserDataFetch(List<TrouserMeasurement> measurements){
        waistBeltET.setText(measurements.get(0).waistBelt);
        frontRiseET.setText(measurements.get(0).frontRise);
        inseamET.setText(measurements.get(0).inseam);
        outseamET.setText(measurements.get(0).outseam);
        seatET.setText(measurements.get(0).seat);
        thighET.setText(measurements.get(0).thigh);
        kneeET.setText(measurements.get(0).knee);
        heapET.setText(measurements.get(0).heap);
        backRiseET.setText(measurements.get(0).backRise);
        hemET.setText(measurements.get(0).hem);

        newTrouserDataAdd(measurements.get(0).id);
    }
}