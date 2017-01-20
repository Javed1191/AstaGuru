package com.infomanav.astaguru;

/**
 * Created by android-javed on 03-10-2016.
 */

public class MyPurchases_Model {

    public String getPur_auc_name() {
        return pur_auc_name;
    }

    public void setPur_auc_name(String pur_auc_name) {
        this.pur_auc_name = pur_auc_name;
    }

    public MyPurchases_Model(String pur_auc_name,String image) {
        this.pur_auc_name = pur_auc_name;
        this.image = image;
    }

    private  String pur_auc_name;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;
}
