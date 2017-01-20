package com.infomanav.astaguru;

/**
 * Created by android-javed on 15-10-2016.
 */

public class Slider_model {
    public String getStr_discription() {
        return str_discription;
    }

    public void setStr_discription(String str_discription) {
        this.str_discription = str_discription;
    }

    public String getStr_img() {
        return str_img;
    }

    public void setStr_img(String str_img) {
        this.str_img = str_img;
    }

    public Slider_model(String str_discription, String str_img) {
        this.str_discription = str_discription;
        this.str_img = str_img;
    }

    String str_discription,str_img;
}
