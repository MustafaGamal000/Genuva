package com.example.genuvaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SakiaParties extends AppCompatActivity {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    ArrayList<PartyModel> sakiaPartiesArrayList = new ArrayList<>();
    GridView gridView;
    SakiaPartyCustomeAdpater adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sakia_party);
        gridView = findViewById(R.id.gridView);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("SakiaParties").getChildren()) {
                    PartyModel sakiaParty = snapshot.getValue(PartyModel.class);
                    sakiaPartiesArrayList.add(sakiaParty);
                }
                adpater = new SakiaPartyCustomeAdpater(getApplicationContext(), R.layout.sakia_row, sakiaPartiesArrayList);
                gridView.setAdapter(adpater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent reserve_tickets=new Intent(SakiaParties.this, PartyBookSakia.class);
                startActivity(reserve_tickets);
                PartyBookSakia.partyModel_Sakia = sakiaPartiesArrayList.get(position);

            }
        });



    }

}
