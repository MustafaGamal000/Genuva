package com.example.genuvaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TicketsCustomeAdapter extends RecyclerView.Adapter<TicketsCustomeAdapter.Holder> {
    private Context context;
    private ArrayList<TicketsModel> ticketsModels;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListener listner) {
        mListener = listner;
    }


    public TicketsCustomeAdapter(Context context, ArrayList<TicketsModel> ticketsModels) {
        this.context = context;
        this.ticketsModels = ticketsModels;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public TicketsCustomeAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.my_tickets_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TicketsCustomeAdapter.Holder holder, final int position) {

        holder.removeLastTextIndex=ticketsModels.get(position).getSelectedSeats();

        holder.partyName.setText(ticketsModels.get(position).getPartyName());
        holder.partyTime.setText(ticketsModels.get(position).getPartyTime());
        holder.totalPrice.setText(String.valueOf(ticketsModels.get(position).getSeatPrice()));
        holder.seatsCount.setText(String.valueOf(ticketsModels.get(position).getSeatCount()));
        holder.selectedSeats.setText(holder.removeLastTextIndex.replace(",", ""));
        Glide.with(context).load(ticketsModels.get(position).getImgUrl()).into(holder.partyImg);
    }

    @Override
    public int getItemCount() {
        return ticketsModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView partyName, partyTime, selectedSeats, seatsCount, totalPrice;
        ImageView partyImg;
        Button cancelBook;
        String removeLastTextIndex;

        public Holder(@NonNull View itemView) {
            super(itemView);
            cancelBook = itemView.findViewById(R.id.cancelBook);
            partyImg = itemView.findViewById(R.id.partImg_MyTickets);
            partyName = itemView.findViewById(R.id.partyName_MyTickets);
            partyTime = itemView.findViewById(R.id.partyTime_MyTickets);
            selectedSeats = itemView.findViewById(R.id.seatsNumber_MyTickets);
            seatsCount = itemView.findViewById(R.id.seatsCount_MyTickets);
            totalPrice = itemView.findViewById(R.id.partyPrice_MyTickets);
            cancelBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
