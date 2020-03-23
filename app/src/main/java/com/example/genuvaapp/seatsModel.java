package com.example.genuvaapp;

public class seatsModel {
    private int Id,state;
    private String ticketPrice;

    public seatsModel() {
    }

    public seatsModel(int id, int state, String ticketPrice) {
        Id = id;
        this.state = state;
        this.ticketPrice = ticketPrice;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
