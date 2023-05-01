package com.example.menfashion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;


public class TailorAccountFragment extends Fragment {

    LinearLayout buttonLogOut;

    public TailorAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tailor_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TailorMainActivity) getActivity()).setToolbarName("Modification");
        buttonLogOut = view.findViewById(R.id.buttonLogOut);
        //signOut
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth firebaseAuth;
                FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (firebaseAuth.getCurrentUser() == null) {
                            //Do anything here which needs to be done after signout is complete
                            Log.e("@data", "MainActivity -> user: @null");
                            Intent intent=new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Log.e("@data", "MainActivity -> user: " + firebaseAuth.getCurrentUser().getUid());
                        }
                    }
                };

                //Init and attach
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.addAuthStateListener(authStateListener);

                //Call signOut()
                firebaseAuth.signOut();
            }
        });
    }
}