package com.example.genuvaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateParty extends AppCompatActivity implements View.OnClickListener {
    Uri uri;
    private Button set_btn, createPartyBtn;
    private ImageView set_img;
    private EditText partyName, partyTime, firstPrice, secondPrice, thirdPrice;
    static String randID = null;

    RadioGroup partyPlace_RG;
    RadioButton selectedPlace_RB;
    String imgURL;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);
        partyName = findViewById(R.id.party_name);
        partyTime = findViewById(R.id.party_time);
        firstPrice = findViewById(R.id.first_class);
        secondPrice = findViewById(R.id.second_class);
        thirdPrice = findViewById(R.id.third_class);
        partyPlace_RG = findViewById(R.id.createPart_RG);
        createPartyBtn = findViewById(R.id.createParty_btn);
        createPartyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPartyData(imgURL);

            }
        });

        set_btn = findViewById(R.id.set_img_btn);
        set_img = findViewById(R.id.setted_img);
        set_btn.setOnClickListener(this);
    }

    public void getPartyData(String imgUrl) {
        randID = databaseReference.push().getKey();

        int radioID = partyPlace_RG.getCheckedRadioButtonId();
        selectedPlace_RB = findViewById(radioID);
        String place = selectedPlace_RB.getText().toString();


        PartyModel PartData = new PartyModel(partyName.getText().toString(), partyTime.getText().toString()
                , firstPrice.getText().toString(), secondPrice.getText().toString(), thirdPrice.getText().toString()
                , imgUrl, randID);

        if (place.equals("Sakiat El Saawy")) {
            databaseReference.child("SakiaParties").child(randID).setValue(PartData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        seatsModel model;
                        Toast.makeText(CreateParty.this, "Party created successfully", Toast.LENGTH_SHORT).show();
                        for (int i = 1; i <= 30; i++) {
                            if (i <= 10) {
                                model = new seatsModel(i, 0, firstPrice.getText().toString());
                            } else if (i <= 20) {
                                model = new seatsModel(i, 0, secondPrice.getText().toString());
                            } else {
                                model = new seatsModel(i, 0, thirdPrice.getText().toString());
                            }
                            databaseReference.child("SakiaParties").child(randID).child("seats").child(String.valueOf(i)).setValue(model);
                        }
                        Intent sakiaParties =new Intent(CreateParty.this, SakiaParties.class);
                        startActivity(sakiaParties);
                        finish();
                    } else {
                        Toast.makeText(CreateParty.this, "Please, Check your party data!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (place.equals("Opera House")) {
            databaseReference.child("OperaParties").child(randID).setValue(PartData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        seatsModel model;
                        Toast.makeText(CreateParty.this, "Party created successfully", Toast.LENGTH_SHORT).show();
                        for (int i = 1; i <= 30; i++) {
                            if (i <= 10) {
                                model = new seatsModel(i, 0, firstPrice.getText().toString());
                            } else if (i <= 20) {
                                model = new seatsModel(i, 0, secondPrice.getText().toString());
                            } else {
                                model = new seatsModel(i, 0, thirdPrice.getText().toString());
                            }
                            databaseReference.child("OperaParties").child(randID).child("seats").child(String.valueOf(i)).setValue(model);
                        }

                    } else {
                        Toast.makeText(CreateParty.this, "Please, Check your party data!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public void onClick(View view) {
        selectImage();

    }

    public void selectImage() {
        Intent pickPhotoImg = new Intent(Intent.ACTION_PICK);
        pickPhotoImg.setType("image/*");
        startActivityForResult(pickPhotoImg, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            set_img.setBackground(null);
            set_img.setImageURI(uri);
            UploadImage(uri);
        }

    }

    private void UploadImage(Uri uri) {
        final String randName = databaseReference.push().getKey();

        storageReference.child("Images").child(randName).putFile(uri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateParty.this, "Image uploaded Successfully", Toast.LENGTH_SHORT).show();
                            storageReference.child("Images").child(randName).getDownloadUrl()
                                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            imgURL = task.getResult().toString();
                                        }
                                    });
                        } else {
                            Toast.makeText(CreateParty.this, "Couldn't upload image", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


}
