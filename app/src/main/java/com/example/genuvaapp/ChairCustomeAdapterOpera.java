package com.example.genuvaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChairCustomeAdapterOpera extends ArrayAdapter {


    public ChairCustomeAdapterOpera(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ArrayList<seatsModel> seatsModels = PartyBookSakia.seats;

        View v = LayoutInflater.from(getContext()).inflate(R.layout.opera_book_row, parent, false);
        final ImageView chair;
        chair = v.findViewById(R.id.chair);

        final PartyBookModelOpera model = (PartyBookModelOpera) getItem(position);

        chair.setImageResource(model.getChairImg());

        chair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (seatsModels.get(position).getState() == 0) {
                    chair.setImageResource(R.drawable.ic_chair);
                    seatsModels.get(position).setState(1);

                }
                else if(seatsModels.get(position).getState()==1){
                    chair.setImageResource(R.drawable.ic_seat_green);
                    seatsModels.get(position).setState(0);

                }
                else {
                    Toast.makeText(getContext(), "This seat is already booked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}
