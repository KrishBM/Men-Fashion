package com.example.menfashion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements AddShopDialog.PopupDialogListener{

    int allDone = 0;
    private EditText nameTextView,numberTextView,emailTextView, passwordTextView, passwordTextView2;
    private CardView Btn;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    private TextView move;
    Button switchCustomer,switchTailor;
    String switchStr="customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        nameTextView = findViewById(R.id.name);
        numberTextView = findViewById(R.id.number);
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        passwordTextView2 = findViewById(R.id.passwd2);
        Btn = findViewById(R.id.btnregister);
        progressbar = findViewById(R.id.progressbar);
        move = findViewById(R.id.move);


        switchCustomer=findViewById(R.id.switchCustomerR);
        switchTailor=findViewById(R.id.switchTailorR);

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
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });

        // Set on Click Listener on Registration button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String name,number,email, password, password2;
        name = nameTextView.getText().toString().trim();
        number = numberTextView.getText().toString().trim();
        email = emailTextView.getText().toString().trim();
        password = passwordTextView.getText().toString().trim();
        password2 = passwordTextView2.getText().toString().trim();

        // Validations for input email and password

        if (TextUtils.isEmpty(email)) {
            stopProgress();
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();

        }else if (TextUtils.isEmpty(name)) {
            stopProgress();
            Toast.makeText(getApplicationContext(),
                            "Please enter name!!",
                            Toast.LENGTH_LONG)
                    .show();

        }else if (TextUtils.isEmpty(number)) {
            stopProgress();
            Toast.makeText(getApplicationContext(),
                            "Please enter number!!",
                            Toast.LENGTH_LONG)
                    .show();

        } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
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

        }else if (number.length() != 10) {
            stopProgress();
            Toast.makeText(getApplicationContext(),
                            "enter 10 digit number!!",
                            Toast.LENGTH_LONG)
                    .show();

        } else if (!password.equals(password2)) {
            stopProgress();
            Toast.makeText(getApplicationContext(),
                            "Both same passwords are not same!!",
                            Toast.LENGTH_LONG)
                    .show();
        } else if (allDone == 0) {



            // create new user or register new user
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                                User user=new User(email,name,number,password,switchStr);
                                FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();

                                        stopProgress();

                                        if(switchStr.equals("tailor")){
                                            openDialog(name,number,email,password);

//                                    startActivity(new Intent(getApplicationContext(),TailorMainActivity.class));
                                        }else{
                                            //Customer
                                            startActivity(new Intent(getApplicationContext(),CustomerMainActivity.class));
                                        }

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                // if the user created intent to login activity
//                                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
//                                        finish();
                                            }
                                        }, 1000);
                                    }
                                });

                            } else {

                                // Registration failed
                                Toast.makeText(
                                                getApplicationContext(),
                                                "Registration failed!!"
                                                        + " Please try again later",
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

    public void openDialog(String name,String number,String email,String password) {
        Bundle bundle = new Bundle();
        bundle.putString("email", email );
        bundle.putString("password",password);
        bundle.putString("name", name );
        bundle.putString("number",number);
        bundle.putString("role",switchStr);

        AddShopDialog addShopDialog =new AddShopDialog();
        addShopDialog.setArguments(bundle);

        addShopDialog.show(getSupportFragmentManager(),"example dialog");
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
            Log.e("@data", "register: current user is: " + uid);
        } else {
            Log.e("@data", "register: current user is @null");
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void applyTexts(String shopName, String address, String shirtPrice, String trouserPrice) {

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


        if (view.getId() == R.id.eye_R2) {

            if (passwordTextView2.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.ic_visibility_off);

                //Show Password
                passwordTextView2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.ic_visibility);

                //Hide Password
                passwordTextView2.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

}