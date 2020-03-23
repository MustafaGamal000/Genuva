package com.example.genuvaapp;

public class TicketsModel {
    private String partyName, partyTime, selectedSeats, partyID, randID, imgUrl;
    private int seatCount, seatPrice;

    public TicketsModel() {
    }

    public TicketsModel(String partyName, String partyTime,
                        String selectedSeats, String partyID,
                        int seatCount, int seatPrice, String randID, String imgUrl) {
        this.partyName = partyName;
        this.partyTime = partyTime;
        this.selectedSeats = selectedSeats;
        this.partyID = partyID;
        this.seatCount = seatCount;
        this.seatPrice = seatPrice;
        this.randID=randID;
        this.imgUrl=imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getRandID() {
        return randID;
    }

    public String getPartyName() {
        return partyName;
    }

    public String getPartyTime() {
        return partyTime;
    }

    public String getSelectedSeats() {
        return selectedSeats;
    }

    public String getPartyID() {
        return partyID;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public int getSeatPrice() {
        return seatPrice;
    }
}
