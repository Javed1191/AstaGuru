package com.infomanav.astaguru;

/**
 * Created by android-javed on 22-10-2016.
 */

public class List_artist_Model {
    public String getArtist_Name() {
        return Artist_Name;
    }

    public void setArtist_Name(String artist_Name) {
        Artist_Name = artist_Name;
    }

    public List_artist_Model(String artist_Name) {
        Artist_Name = artist_Name;
    }

    String Artist_Name;
}
