package com.example.genuvaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Register extends AppCompatActivity {
    EditText email, password, rePassword, username, userNumber;
    Button signUp;
    ImageView userpicture;
    private Uri uri;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.username);
        userNumber = findViewById(R.id.userNumberLogin);
        userpicture = findViewById(R.id.profilePicture);
        userpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPhoto();
            }
        });
        email = findViewById(R.id.email_RegisterPage);
        password = findViewById(R.id.password_RegisterPage);
        rePassword = findViewById(R.id.re_password_RegisterPage);
        signUp = findViewById(R.id.signUp_RegisterPage);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });


    }

    private void SelectPhoto() {
        Intent selectImg = new Intent(Intent.ACTION_PICK);
        selectImg.setType("image/*");
        startActivityForResult(selectImg, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            userpicture.setBackground(null);
            userpicture.setImageURI(uri);
        }
    }


    private void createUser() {

        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String RePassword = rePassword.getText().toString();
        String userName = username.getText().toString();
        String UserNumber = userNumber.getText().toString();

        if(uri==null){
            Toast.makeText(this, "Please select your profile picture", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(UserNumber)) {
            Toast.makeText(this, "Please enter your number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(RePassword)) {
            Toast.makeText(this, "Please Enter Password Again", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            if (TextUtils.equals(Password, RePassword)) {
            } else {
                Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show();
                return;
            }

        }


        firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            String Uid = task.getResult().getUser().getUid();
                            UploadProfileImg(Uid);

                            Intent return_login = new Intent(Register.this, Login.class);
                            startActivity(return_login);
                            finish();
                        } else {
                            Toast.makeText(Register.this, "Could not Register, Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void UploadProfileImg(final String Uid) {
        if(uri !=null) {
            storageReference.child("ProfilePicture").child(Uid).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        storageReference.child("ProfilePicture").child(Uid).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    String Url = task.getResult().toString();
                                    SaveDataInRealTime(Url, Uid);
                                }
                            }
                        });
                    }
                }
            });
        } else {
            Toast.makeText(this, "Could not upload image", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void SaveDataInRealTime(String url, String uid) {
        String userNameRealTime = username.getText().toString();
        String userNumberRealTime = userNumber.getText().toString();
        UserModel userModel=new UserModel(userNameRealTime, userNumberRealTime, url);
        reference.child("Users").child(uid).setValue(userModel);

    }


}
