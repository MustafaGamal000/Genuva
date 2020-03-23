package com.example.genuvaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
 String ID;
 ImageView profilePic;
 TextView username, userNumber;
 DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilePic=findViewById(R.id.profilePicture);
        username=findViewById(R.id.username_profilePage);
        userNumber=findViewById(R.id.userNumber_profilePage);
        ID=Login.userID;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Url=dataSnapshot.child("Users").child(ID).child("url").getValue().toString();
                Picasso.with(getApplicationContext()).load(Url).into(profilePic);

                String UserName=dataSnapshot.child("Users").child(ID).child("username").getValue().toString();
                username.setText(UserName);

                String UserNumber=dataSnapshot.child("Users").child(ID).child("userNumber").getValue().toString();
                userNumber.setText(UserNumber);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
