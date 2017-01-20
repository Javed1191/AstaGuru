package com.infomanav.astaguru;

/**
 * Created by android-javed on 04-10-2016.
 */

public class Bid_History_Model {



    public String getStr_price_doller() {
        return str_price_doller;
    }

    public void setStr_price_doller(String str_price_doller) {
        this.str_price_doller = str_price_doller;
    }

    public String getStr_timedate() {
        return str_timedate;
    }

    public void setStr_timedate(String str_timedate) {
        this.str_timedate = str_timedate;
    }

    public String getStr_price_rupee() {
        return str_price_rupee;
    }

    public void setStr_price_rupee(String str_price_rupee) {
        this.str_price_rupee = str_price_rupee;
    }

    public String getStr_nickname() {
        return str_nickname;
    }

    public void setStr_nickname(String str_nickname) {
        this.str_nickname = str_nickname;
    }

    private String str_price_doller;

    public Bid_History_Model(String str_price_doller, String str_price_rupee, String str_timedate, String str_nickname) {
        this.str_price_doller = str_price_doller;
        this.str_price_rupee = str_price_rupee;
        this.str_timedate = str_timedate;
        this.str_nickname = str_nickname;
    }

    private String str_price_rupee;
    private String str_timedate;
    private  String str_nickname;
}
