package com.infomanav.astaguru;

/**
 * Created by android-javed on 22-10-2016.
 */

public class Second_img_Model {
    String imgg_url;

    public String getImg_title() {
        return img_title;
    }

    public Second_img_Model(String imgg_url, String img_title, String img_date) {
        this.imgg_url = imgg_url;
        this.img_title = img_title;
        this.img_date = img_date;
    }

    public void setImg_title(String img_title) {
        this.img_title = img_title;
    }

    public String getImg_date() {
        return img_date;
    }

    public void setImg_date(String img_date) {
        this.img_date = img_date;
    }

    public String getImgg_url() {
        return imgg_url;
    }

    public void setImgg_url(String imgg_url) {
        this.imgg_url = imgg_url;
    }

    String img_title;
    String img_date;
}
