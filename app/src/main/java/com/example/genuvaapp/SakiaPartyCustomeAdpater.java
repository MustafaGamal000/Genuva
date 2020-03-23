package com.example.genuvaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SakiaPartyCustomeAdpater extends ArrayAdapter {
    public SakiaPartyCustomeAdpater(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= LayoutInflater.from(getContext()).inflate(R.layout.sakia_row, parent, false);

        ImageView partyImg;
        TextView partyName, partyDate;

        partyImg=v.findViewById(R.id.party_img);
        partyName=v.findViewById(R.id.name_party_skaia);
        partyDate=v.findViewById(R.id.date_party_sakia);

        PartyModel sakiaParty=(PartyModel) getItem(position);




        Picasso.with(getContext()).load(sakiaParty.getPartyImg()).into(partyImg);
        partyName.setText(sakiaParty.getPartyName());
        partyDate.setText(sakiaParty.getPartyTime());


        return v;
    }
}
