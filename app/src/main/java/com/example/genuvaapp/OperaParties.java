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

public class OperaParties extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    static ArrayList<PartyModel> operaPartiesArrayList = new ArrayList<>();
    GridView operaGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opera_parties);
        operaGridView = findViewById(R.id.operaGridView);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("OperaParties").getChildren()) {
                    PartyModel operaParty = snapshot.getValue(PartyModel.class);
                    operaPartiesArrayList.add(operaParty);
                }
                OperaPartiesCustomAdapter adpater = new OperaPartiesCustomAdapter(getApplicationContext()
                        , R.layout.opera_row, operaPartiesArrayList);

                operaGridView.setAdapter(adpater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        operaGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent bookParty = new Intent(OperaParties.this, PartyBookSakia.class);
                startActivity(bookParty);
                PartyBookOpera.partyModel_Opera = operaPartiesArrayList.get(i);

            }
        });
    }
}
