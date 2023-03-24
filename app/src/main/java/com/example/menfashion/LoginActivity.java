package com.example.menfashion;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private int allDone = 0;
    private EditText emailTextView, passwordTextView;
    private CardView Btn;
    private TextView move;
    private ProgressBar progressbar;
    Button switchCustomer,switchTailor;
    private FirebaseAuth mAuth;
    String switchStr="customer";
    String userRole;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // taking instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        Btn = findViewById(R.id.login);
        progressbar = findViewById(R.id.progressbar);
        move = findViewById(R.id.move);

        switchCustomer=findViewById(R.id.switchCustomerL);
        switchTailor=findViewById(R.id.switchTailorL);

        switchTailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchStr="tailor";
                switchTailor.setBackgroundColor(getResources().getColor(android.R.color.white));
                switchCustomer.setBackgroundResource(R.drawable.header_bg_color);
                switchTailor.setTextColor(getResources().getColor(android.R.color.black));
                switchCustomer.setTextColor(getResources().getColor(android.R.color.white));
            }
        });
        switchCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchStr="customer";
                switchCustomer.setBackgroundColor(getResources().getColor(android.R.color.white));
                switchTailor.setBackgroundResource(R.drawable.header_bg_color);
                switchCustomer.setTextColor(getResources().getColor(android.R.color.black));
                switchTailor.setTextColor(getResources().getColor(android.R.color.white));
            }
        });


        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
            }
        });

        // Set on Click Listener on Sign-in button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });
    }

    private void loginUserAccount() {

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password;
        email = emailTextView.getText().toString().trim();
        password = passwordTextView.getText().toString().trim();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            stopProgress();
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();

        } else if (TextUtils.isEmpty(password)) {
            stopProgress();
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();

        } else if (password.length() < 8) {
            stopProgress();
            Toast.makeText(getApplicationContext(),
                            "enter minimum 8 length password!!",
                            Toast.LENGTH_LONG)
                    .show();

        } else if (allDone == 0) {
            // signin existing user
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(
                                        @NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),
                                                        "Login successful!!",
                                                        Toast.LENGTH_LONG)
                                                .show();

                                        // hide the progress bar
                                        stopProgress();

                                        DatabaseReference userRef = database.getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    userRole = snapshot.child("role").getValue(String.class);

                                                    assert userRole != null;
                                                    if(userRole.equals("tailor")){
                                                        startActivity(new Intent(LoginActivity.this, TailorMainActivity.class));
//                                                        finish();
                                                    }
                                                    if(userRole.equals("customer")){
                                                        startActivity(new Intent(LoginActivity.this, CustomerMainActivity.class));
//                                                        finish();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                // Handle the error
                                            }
                                        });

                                        // if sign-in is successful
                                        // intent to home activity
//                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                                        finish();

                                    } else {

                                        // sign-in failed
                                        Toast.makeText(getApplicationContext(),
                                                        "Login failed!!",
                                                        Toast.LENGTH_LONG)
                                                .show();

                                        // hide the progress bar
                                        stopProgress();
                                    }
                                }
                            });
        } else {
            stopProgress();
        }


    }

    private void stopProgress() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            Log.e("@data", "login: current user is: " + uid);

        } else {
            Log.e("@data", "login: current user is @null");

        }
    }

    @Override
    public void onBackPressed() {
    }

    public void hide_show_password_R(View view) {

        if (view.getId() == R.id.eye_R) {

            if (passwordTextView.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.ic_visibility_off);

                //Show Password
                passwordTextView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.ic_visibility);

                //Hide Password
                passwordTextView.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }

    }


}