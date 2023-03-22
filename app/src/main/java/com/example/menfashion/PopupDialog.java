package com.example.menfashion;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class PopupDialog extends AppCompatDialogFragment {

    private EditText EshopName,Eaddress,EshirtPrice,EtrouserPrice;
    private PopupDialogListener listener;


    ImageView logoView;
    // Uri indicates, where the image will be picked from
    private Uri filePath;

    private Context context;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Shop shop;
    String name,imageURL="",trouserPrice,number,shirtPrice,email,address,password,shopName;
    Button uploadButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.popup_tailor,null);

        email = this.getArguments().getString("email");
        name = this.getArguments().getString("name");
        number = this.getArguments().getString("number");
        password = this.getArguments().getString("password");

        logoView=view.findViewById(R.id.logoView);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("ShopData");

        shop = new Shop();

        EshopName=view.findViewById(R.id.shop_name);
        Eaddress=view.findViewById(R.id.address);
        EshirtPrice=view.findViewById(R.id.shirt_price);
        EtrouserPrice=view.findViewById(R.id.trouser_price);
        uploadButton=view.findViewById(R.id.uploadButton);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        builder.setView(view)
                .setTitle("Create Shop")
                .setNeutralButton("Create Shop", (dialogInterface, i) -> {

                    if(TextUtils.isEmpty(EshopName.getText()) || TextUtils.isEmpty(Eaddress.getText()) || TextUtils.isEmpty(EshirtPrice.getText()) || TextUtils.isEmpty(EtrouserPrice.getText())){
                        Toast.makeText(getContext(), "Fill All The Details...!!", Toast.LENGTH_SHORT).show();
                    }else {

//                        if(uploadSuccesFlag.equals("s") && !(uploadImageURL.isEmpty())){
                        shopName= EshopName.getText().toString();
                        address= Eaddress.getText().toString();
                        shirtPrice= EshirtPrice.getText().toString();
                        trouserPrice= EtrouserPrice.getText().toString();


                        uploadImage();

//                        addDatatoFirebase(shopName,address,imageURL,shirtPrice,trouserPrice);//TODO

                    }

                });
        uploadButton.setOnClickListener(v -> SelectImage());

        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), filePath);
                logoView.setImageBitmap(bitmap);
                logoView.setVisibility(View.VISIBLE);
                logoView.setAdjustViewBounds(true);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


    // UploadImage method
    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            String path="images/" + UUID.randomUUID().toString();
            StorageReference ref = storageReference.child(path);

            Resources res = getResources();
            imageURL=res.getString(R.string.storage_base_url)+path;
            // adding listeners on upload
            // or failure of image
            Log.d("eeeeeeeeee",imageURL);


            ref.putFile(filePath).
                    addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully
                        // Dismiss dialog
                        progressDialog.dismiss();
                        Toast.makeText(context, "Image Uploaded!!", Toast.LENGTH_SHORT).show();

                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                addDatatoFirebase(shopName,address,imageURL,shirtPrice,trouserPrice);//TODO
//                                startActivity(new Intent(context,TailorMainActivity.class));
                            }
                        });

                    }).
                    addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(context, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                }
                            });
//            addDatatoFirebase(shopName,address,imageURL,shirtPrice,trouserPrice);//TODO
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public interface PopupDialogListener{
        void applyTexts(String shopName,String address,String shirtPrice, String trouserPrice);
    }

    private void SelectImage() {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    private void addDatatoFirebase(String shopName, String address, String imageURL,String shirtPrice,String trouserPrice) {
        // below 3 lines of code is used to set
        // data in our object class.
        shop.setSname(shopName);
        shop.setAddress(address);
        shop.setLogo(imageURL);
        shop.setShirtPrice(shirtPrice);
        shop.setTrouserPrice(trouserPrice);

        // we are use add value event listener method
        // which is called with database reference.
        String Skey = databaseReference.push().getKey();
        databaseReference.child(Skey).setValue(shop).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // after adding this data we are showing toast message.
                Toast.makeText(context, "data added", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
