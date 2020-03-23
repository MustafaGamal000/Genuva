package com.example.genuvaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyTickets extends AppCompatActivity implements TicketsCustomeAdapter.OnItemClickListener {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    RecyclerView myTickets_RV;

    private FirebaseUser currentUser;
    private String userID;
    private ArrayList<TicketsModel> ticketsModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);
        myTickets_RV = findViewById(R.id.myTickets_RV);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = currentUser.getUid();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.child("Users").child(userID)
                        .child("MyTickets").getChildren()) {
                    TicketsModel tModel = snapshot.getValue(TicketsModel.class);
                    ticketsModelArrayList.add(tModel);

                    myTickets_RV.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            RecyclerView.VERTICAL, false));
                    TicketsCustomeAdapter adapter = new TicketsCustomeAdapter(getApplicationContext(), ticketsModelArrayList);
                    myTickets_RV.setAdapter(adapter);
                    adapter.setOnItemClickListner(MyTickets.this);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onItemClick(int position) {

        reference.child("Users").child(userID).child("MyTickets")
                .child(ticketsModelArrayList.get(position).getRandID()).removeValue();


    }
}
