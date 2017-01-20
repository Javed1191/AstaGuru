package com.infomanav.astaguru;

/**
 * Created by android-javed on 03-10-2016.
 */

public class Category_Model {

    public Category_Model(String category_name) {
        this.Category_name = category_name;
    }

    public String getCategory_name() {
        return Category_name;
    }

    public void setCategory_name(String category_name) {
        this.Category_name = category_name;
    }

    private String Category_name;
}
