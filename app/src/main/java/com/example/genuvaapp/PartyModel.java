package com.example.genuvaapp;

public class PartyModel {
    private String partyName, partyTime, firstPrice, secondPrice, thirdPrice, partyImg, partyId;

    public PartyModel() {
    }

    public PartyModel(String partyName, String partyTime, String firstPrice, String secondPrice, String thirdPrice, String partyImg, String partyId) {
        this.partyName = partyName;
        this.partyTime = partyTime;
        this.firstPrice = firstPrice;
        this.secondPrice = secondPrice;
        this.thirdPrice = thirdPrice;
        this.partyImg = partyImg;
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyTime() {
        return partyTime;
    }

    public void setPartyTime(String partyTime) {
        this.partyTime = partyTime;
    }

    public String getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(String firstPrice) {
        this.firstPrice = firstPrice;
    }

    public String getSecondPrice() {
        return secondPrice;
    }

    public void setSecondPrice(String secondPrice) {
        this.secondPrice = secondPrice;
    }

    public String getThirdPrice() {
        return thirdPrice;
    }

    public void setThirdPrice(String thirdPrice) {
        this.thirdPrice = thirdPrice;
    }

    public String getPartyImg() {
        return partyImg;
    }

    public void setPartyImg(String partyImg) {
        this.partyImg = partyImg;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
}
