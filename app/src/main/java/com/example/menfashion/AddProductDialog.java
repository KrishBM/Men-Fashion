package com.example.menfashion;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

public class AddProductDialog extends AppCompatDialogFragment {

    private EditText EfabricType,EfabricColor,EfabricPrice;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch availableSwitch;

    ImageView ImgView;
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    private Context context;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;
//    int selectedId=-1;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference productDatabaseReference;

    RadioButton shirtRB,trouserRB;
    String imageURL="",fabricPrice,fabricType,fabricColor,clothType,fabricAvailability,CurrentTailorID;
    Button uploadButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=requireActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.add_product,null);

        ImgView=view.findViewById(R.id.ImgView);

        shirtRB=view.findViewById(R.id.radioShirt);
        trouserRB=view.findViewById(R.id.radioTrouser);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        productDatabaseReference = firebaseDatabase.getReference().child("ProductData");

        CurrentTailorID=FirebaseAuth.getInstance().getCurrentUser().getUid();

        availableSwitch=view.findViewById(R.id.availableSwitch);
        EfabricType=view.findViewById(R.id.fabric_type);
        EfabricColor=view.findViewById(R.id.fabric_color);
        EfabricPrice=view.findViewById(R.id.fabric_price);
        uploadButton=view.findViewById(R.id.uploadButton);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        builder.setView(view)
                .setTitle("Add Product")
                .setPositiveButton("ADD", (dialogInterface, i) -> {

//                    selectedId = radioGroup.getCheckedRadioButtonId();
//                    genderradioButton = view.findViewById(selectedId);

                    if(TextUtils.isEmpty(EfabricType.getText()) || TextUtils.isEmpty(EfabricColor.getText()) || TextUtils.isEmpty(EfabricPrice.getText())){
                        Toast.makeText(getContext(), "Fill All The Details...!!", Toast.LENGTH_SHORT).show();
                    }else if (ImgView.getVisibility()==View.GONE) {
                        Toast.makeText(getContext(), "Please upload image...!!", Toast.LENGTH_SHORT).show();
                    } else if (!shirtRB.isChecked() && !trouserRB.isChecked()) {
                        Toast.makeText(getContext(), "Please select cloth type...!!", Toast.LENGTH_SHORT).show();
                    } else {

//                        if(uploadSuccesFlag.equals("s") && !(uploadImageURL.isEmpty())){
                        if(shirtRB.isChecked()){
                            clothType=shirtRB.getText().toString();
                        }else {
                            clothType=trouserRB.getText().toString();
                        }

                        fabricType= EfabricType.getText().toString();
                        fabricColor= EfabricColor.getText().toString();
                        fabricPrice= EfabricPrice.getText().toString();
                        if (availableSwitch.isChecked())
                            fabricAvailability = availableSwitch.getTextOn().toString();
                        else
                            fabricAvailability = availableSwitch.getTextOff().toString();

                        uploadImage();

//                        startActivity(new Intent(getActivity(),TailorMainActivity.class));//TODO

                    }

                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    // Negative button clicked
                    dialogInterface.cancel(); // Disable dialog
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
                ImgView.setImageBitmap(bitmap);
                ImgView.setVisibility(View.VISIBLE);
                ImgView.setAdjustViewBounds(true);
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

            String path="fabricImage/" + UUID.randomUUID().toString();
            imageURL=getResources().getString(R.string.storage_base_url)+path;
            StorageReference ref = storageReference.child(path);



// percentage on the dialog box
            ref.putFile(filePath).
                    addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully
                        // Dismiss dialog
                        progressDialog.dismiss();
                        Toast.makeText(context, "Image Uploaded!!", Toast.LENGTH_SHORT).show();

                        ref.getDownloadUrl().addOnSuccessListener(uri -> {
//                            Log.d("imggggggggggggggggg",imageURL);

                            addDatatoFirebase(fabricType,fabricColor,fabricPrice,clothType,fabricAvailability,imageURL,CurrentTailorID);//TODO
//                                startActivity(new Intent(context,TailorMainActivity.class));
                        });

                    }).
                    addOnFailureListener(e -> {

                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast.makeText(context, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int)progress + "%");
                    });
//            addDatatoFirebase(shopName,address,imageURL,shirtPrice,trouserPrice);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
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

    private void addDatatoFirebase(String fabricType,String fabricColor,String fabricPrice,String clothType,String fabricAvailability,String imageURL,String CurrentTailorID) {
        // below 3 lines of code is used to set
        // data in our object class.

        Product product = new Product(fabricType,fabricColor,imageURL,fabricPrice,clothType,fabricAvailability,CurrentTailorID);

        // we are use add value event listener method
        // which is called with database reference.

        productDatabaseReference.child(productDatabaseReference.push().getKey()).setValue(product).addOnSuccessListener(unused -> {
            // after adding this data we are showing toast message.

            Toast.makeText(context, "data added", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getActivity(),TailorMainActivity.class));
        });


    }
}
