package com.example.genuvaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PartyBookOpera extends AppCompatActivity {
    GridView operaBookGridView;
    ArrayList<PartyBookModelOpera> partyBookModelOperaArrayList = new ArrayList<>();
    public static PartyModel partyModel_Opera;
    private TextView firstPrice, secondPrice, thirdPrice, selectedSeats, seatsPrice;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    public static ArrayList<seatsModel> seats = new ArrayList<>();
    seatsModel sModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_book_opera);
        firstPrice = findViewById(R.id.first_class_price_opera);
        secondPrice = findViewById(R.id.second_class_price_opera);
        thirdPrice = findViewById(R.id.third_class_price_opera);
        selectedSeats = findViewById(R.id.reserved_seat_opera);
        seatsPrice = findViewById(R.id.total_tickets_price_opera);
        operaBookGridView.findViewById(R.id.gridViewBook_opera);

        PartyBookModelOpera chair1 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair1);
        PartyBookModelOpera chair2 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair2);
        PartyBookModelOpera chair3 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair3);
        PartyBookModelOpera chair4 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair4);
        PartyBookModelOpera chair5 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair5);
        PartyBookModelOpera chair6 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair6);
        PartyBookModelOpera chair7 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair7);
        PartyBookModelOpera chair8 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair8);
        PartyBookModelOpera chair9 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair9);
        PartyBookModelOpera chair10 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair10);
        PartyBookModelOpera chair11 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair11);
        PartyBookModelOpera chair12 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair12);
        PartyBookModelOpera chair13 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair13);
        PartyBookModelOpera chair14 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair14);
        PartyBookModelOpera chair15 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair15);
        PartyBookModelOpera chair16 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair16);
        PartyBookModelOpera chair17 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair17);
        PartyBookModelOpera chair18 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair18);
        PartyBookModelOpera chair19 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair19);
        PartyBookModelOpera chair20 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair20);
        PartyBookModelOpera chair21 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair21);
        PartyBookModelOpera chair22 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair22);
        PartyBookModelOpera chair23 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair23);
        PartyBookModelOpera chair24 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair24);
        PartyBookModelOpera chair25 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair25);
        PartyBookModelOpera chair26 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair26);
        PartyBookModelOpera chair27 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair27);
        PartyBookModelOpera chair28 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair28);
        PartyBookModelOpera chair29 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair29);
        PartyBookModelOpera chair30 = new PartyBookModelOpera(R.drawable.ic_seat_green);
        partyBookModelOperaArrayList.add(chair30);

        ChairCustomeAdapterOpera adapter=new ChairCustomeAdapterOpera(getApplicationContext(), R.layout.opera_book_row, partyBookModelOperaArrayList);
        operaBookGridView.setAdapter(adapter);


        firstPrice.setText(partyModel_Opera.getFirstPrice());
        secondPrice.setText(partyModel_Opera.getSecondPrice());
        thirdPrice.setText(partyModel_Opera.getThirdPrice());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.child("OperaParties")
                        .child(partyModel_Opera.getPartyId()).child("seats").getChildren()) {
                    sModel = snapshot.getValue(seatsModel.class);
                    seats.add(sModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        operaBookGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                selectedSeats.setText("" + seats.get(i).getId() + " ,");
                int totalPrice = 0;
                int firstPrice = Integer.parseInt(partyModel_Opera.getFirstPrice());
                int secondPrice = Integer.parseInt(partyModel_Opera.getSecondPrice());
                int thirdPrice = Integer.parseInt(partyModel_Opera.getThirdPrice());


                if (seats.get(i).getId() <= 10) {
                    totalPrice += firstPrice;
                } else if (seats.get(i).getId() <= 20) {
                    totalPrice += secondPrice;
                } else {
                    totalPrice += thirdPrice;
                }
                seatsPrice.setText("" + totalPrice + " L.E");

            }
        });
    }
}
