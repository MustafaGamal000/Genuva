package com.example.genuvaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PartyBookSakia extends AppCompatActivity {
    GridView gridView;
    ArrayList<PartyBookModelSakia> partyBookModelArrayList = new ArrayList<>();
    public static PartyModel partyModel_Sakia;
    TextView firstPrice, secondPrice, thirdPrice;
    public static TextView seatsPrice;
    public static TextView selectedSeats;
    DatabaseReference dbRef;
    public static ArrayList<seatsModel> seats = new ArrayList<>();
    seatsModel sModel;
    static Button bookNow;
    public static int totalPrice;
    public static String choosenSeats;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_book);

        dbRef = FirebaseDatabase.getInstance().getReference();


        bookNow = findViewById(R.id.bookNowSakia);


        selectedSeats = findViewById(R.id.the_reserved_seats);
        seatsPrice = findViewById(R.id.total_tickets_price);

        firstPrice = findViewById(R.id.first_class_price);
        secondPrice = findViewById(R.id.second_class_price);
        thirdPrice = findViewById(R.id.third_class_price);
        gridView = findViewById(R.id.gridViewBook);




        firstPrice.setText(partyModel_Sakia.getFirstPrice());
        secondPrice.setText(partyModel_Sakia.getSecondPrice());
        thirdPrice.setText(partyModel_Sakia.getThirdPrice());

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    seats.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("SakiaParties")
                        .child(partyModel_Sakia.getPartyId()).child("seats").getChildren()) {
                    sModel = snapshot.getValue(seatsModel.class);
                    seats.add(sModel);


                    ChairsCustomeAdapterSakia adapter = new ChairsCustomeAdapterSakia(getApplicationContext(),
                            R.layout.party_book_sakia_row, seats);
                    gridView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void setSeatsData() {
        selectedSeats.setText("");
        seatsPrice.setText("0 L.E");
        choosenSeats = null;
        totalPrice = 0;
        for (int i = 0; i < ChairsCustomeAdapterSakia.seatPosition.size(); i++) {
            if (ChairsCustomeAdapterSakia.seatPosition.get(i).getState() == 1) {
                choosenSeats = ChairsCustomeAdapterSakia.seatPosition.get(i).getId() + " , ";
                selectedSeats.append(choosenSeats);


                int firstPrice = Integer.parseInt(partyModel_Sakia.getFirstPrice());
                int secondPrice = Integer.parseInt(partyModel_Sakia.getSecondPrice());
                int thirdPrice = Integer.parseInt(partyModel_Sakia.getThirdPrice());

                if (ChairsCustomeAdapterSakia.seatPosition.get(i).getId() <= 10) {
                    totalPrice += firstPrice;
                }
                if (ChairsCustomeAdapterSakia.seatPosition.get(i).getId() <= 20
                        && ChairsCustomeAdapterSakia.seatPosition.get(i).getId() > 10) {
                    totalPrice += secondPrice;
                }
                if (ChairsCustomeAdapterSakia.seatPosition.get(i).getId() <= 30 &&
                        ChairsCustomeAdapterSakia.seatPosition.get(i).getId() > 20) {
                    totalPrice += thirdPrice;
                }

                seatsPrice.setText("" + totalPrice + " L.E");

            }


        }

    }

}
