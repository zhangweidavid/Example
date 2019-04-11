package com.example.prizedraw;

public class LotteryBase {
    private String number;

    private String dataPeriod;

    private String text;

    private int textInt;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDataPeriod() {
        return dataPeriod;
    }

    public void setDataPeriod(String dataPeriod) {
        this.dataPeriod = dataPeriod;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextInt() {
        return textInt;
    }

    public void setTextInt(int textInt) {
        this.textInt = textInt;
    }

    @Override
    public String toString() {
        return "LotterBase [number=" + number + ", dataPeriod=" + dataPeriod + ", text=" + text + ", textInt=" + textInt
                + "]";
    }

}
