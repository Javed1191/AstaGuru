package com.infomanav.astaguru;

/**
 * Created by fox-2 on 12/7/2016.
 */

public class Model_History {

    String str_bid_name;
    String str_bid_d;

    public Model_History(String str_bid_name, String str_bid_d, String str_bid_r, String str_bid_date) {
        this.str_bid_name = str_bid_name;
        this.str_bid_d = str_bid_d;
        this.str_bid_r = str_bid_r;
        this.str_bid_date = str_bid_date;
    }

    public String getStr_bid_name() {
        return str_bid_name;
    }

    public void setStr_bid_name(String str_bid_name) {
        this.str_bid_name = str_bid_name;
    }

    public String getStr_bid_d() {
        return str_bid_d;
    }

    public void setStr_bid_d(String str_bid_d) {
        this.str_bid_d = str_bid_d;
    }

    public String getStr_bid_r() {
        return str_bid_r;
    }

    public void setStr_bid_r(String str_bid_r) {
        this.str_bid_r = str_bid_r;
    }

    public String getStr_bid_date() {
        return str_bid_date;
    }

    public void setStr_bid_date(String str_bid_date) {
        this.str_bid_date = str_bid_date;
    }

    String str_bid_r;
    String str_bid_date;
}
