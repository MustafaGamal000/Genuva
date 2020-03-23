package com.example.genuvaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.genuvaapp.PartyBookSakia.bookNow;
import static com.example.genuvaapp.PartyBookSakia.seats;

public class ChairsCustomeAdapterSakia extends ArrayAdapter {

    TicketsModel tModel;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    public static ArrayList<seatsModel> seatPosition = new ArrayList<>();

    private FirebaseAuth mAuth;

    String userID;
    FirebaseUser currenUser;


    public ChairsCustomeAdapterSakia(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View v = LayoutInflater.from(getContext()).inflate(R.layout.party_book_sakia_row, parent, false);

        final TextView chair;
        chair = v.findViewById(R.id.chair);


        final seatsModel model = (seatsModel) getItem(position);

        chair.setText(String.valueOf(model.getId()));


        if (seats.get(position).getState() == 0) {
            chair.setBackgroundResource(R.drawable.ic_seat_green);
        }
        if (seats.get(position).getState() == 2) {
            chair.setBackgroundResource(R.drawable.ic_seat_red);
        }

        chair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (seats.get(position).getState() == 0) //Unselected
                {
                    chair.setBackgroundResource(R.drawable.ic_chair);
                    seats.get(position).setState(1);
                    seatPosition.add(seats.get(position));

                } else if (seats.get(position).getState() == 1) //Selected
                {
                    chair.setBackgroundResource(R.drawable.ic_seat_green);
                    seats.get(position).setState(0);
                    seatPosition.remove(seats.get(position));
                } else {
                    Toast.makeText(getContext(), "This seat is already booked", Toast.LENGTH_SHORT).show();
                }
                PartyBookSakia.setSeatsData();

            }
        });

        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String randID = reference.push().getKey();
                if (seatPosition.size() == 0) {
                    Toast.makeText(getContext(), "Please select seats", Toast.LENGTH_SHORT).show();
                }
                for (int i = 0; i < seatPosition.size(); i++) {

                    if (seatPosition.get(i).getState() == 1) {
                        chair.setBackgroundResource(R.drawable.ic_seat_red);
                        final int finalI = i;
                        tModel = new TicketsModel(PartyBookSakia.partyModel_Sakia.getPartyName(),
                                PartyBookSakia.partyModel_Sakia.getPartyTime(),
                                PartyBookSakia.selectedSeats.getText().toString(),
                                PartyBookSakia.partyModel_Sakia.getPartyId(),
                                seatPosition.size(),
                                PartyBookSakia.totalPrice, randID
                                ,PartyBookSakia.partyModel_Sakia.getPartyImg()
                        );
                        reference.child("SakiaParties").child(PartyBookSakia.partyModel_Sakia.getPartyId())
                                .child("seats").child(String.valueOf(seatPosition.get(finalI).getId()))
                                .child("state").setValue(2).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Seats are booked successfully, Please pay the money in 3 hours", Toast.LENGTH_LONG).show();

                                    currenUser = FirebaseAuth.getInstance().getCurrentUser();
                                    userID = currenUser.getUid();


                                    reference.child("Users").child(userID).child("MyTickets").
                                            child(randID).setValue(tModel);

                                } else {
                                    Toast.makeText(getContext(), "An error is occured, please try again!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

//                        reference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });

                    }
                }

            }
        });

        seatPosition.clear();

        return v;
    }
}
