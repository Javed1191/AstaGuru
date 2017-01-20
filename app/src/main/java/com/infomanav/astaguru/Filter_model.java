package com.infomanav.astaguru;

/**
 * Created by android-javed on 19-10-2016.
 */

public class Filter_model {
    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    String artist_name;

    public Filter_model(String artist_name, String category_name) {
        this.artist_name = artist_name;
        this.category_name = category_name;
    }

    String category_name;
}
