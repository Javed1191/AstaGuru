package com.infomanav.astaguru;

/**
 * Created by android-javed on 22-10-2016.
 */

public class List_category_Model {
    public String getCategory_Name() {
        return Category_Name;
    }

    public void setCategory_Name(String category_Name) {
        Category_Name = category_Name;
    }

    public List_category_Model(String category_Name) {
        Category_Name = category_Name;
    }

    String Category_Name;
}
