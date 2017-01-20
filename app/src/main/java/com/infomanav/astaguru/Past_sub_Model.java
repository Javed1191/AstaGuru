package com.infomanav.astaguru;

/**
 * Created by android-javed on 13-10-2016.
 */

public class Past_sub_Model
{

    public String getStr_title() {
        return str_title;
    }

    public void setStr_title(String str_title) {
        this.str_title = str_title;
    }

    public String getStr_description() {
        return str_description;
    }

    public void setStr_description(String str_description) {
        this.str_description = str_description;
    }

    public String getStr_artistid() {
        return str_artistid;
    }

    public void setStr_artistid(String str_artistid) {
        this.str_artistid = str_artistid;
    }

    public String getStr_thumbnail() {
        return str_thumbnail;
    }

    public void setStr_thumbnail(String str_thumbnail) {
        this.str_thumbnail = str_thumbnail;
    }

    public String getStr_image() {
        return str_image;
    }

    public void setStr_image(String str_image) {
        this.str_image = str_image;
    }

    public String getStr_productsize() {
        return str_productsize;
    }

    public void setStr_productsize(String str_productsize) {
        this.str_productsize = str_productsize;
    }

    public String getStr_small_img() {
        return str_small_img;
    }

    public void setStr_small_img(String str_small_img) {
        this.str_small_img = str_small_img;
    }

    public String getStr_Profile() {
        return str_Profile;
    }

    public void setStr_Profile(String str_Profile) {
        this.str_Profile = str_Profile;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getStr_category() {
        return str_category;
    }

    public void setStr_category(String str_category) {
        this.str_category = str_category;
    }

    public String getStr_productid() {
        return str_productid;
    }

    public void setStr_productid(String str_productid) {
        this.str_productid = str_productid;
    }

    public String getPricers() {
        return pricers;
    }

    public void setPricers(String pricers) {
        this.pricers = pricers;
    }

    public String getPriceus() {
        return priceus;
    }

    public void setPriceus(String priceus) {
        this.priceus = priceus;
    }

    public boolean getis_front() {
        return is_front;
    }

    public void setIs_front(boolean is_front) {
        this.is_front = is_front;
    }

    public String getDollarRate() {
        return DollarRate;
    }

    public void setDollarRate(String dollarRate) {
        DollarRate = dollarRate;
    }

    public String getStr_collectors() {
        return str_collectors;
    }

    public void setStr_collectors(String str_collectors) {
        this.str_collectors = str_collectors;
    }

    public Past_sub_Model(String str_title, String str_description, String str_artistid, String str_thumbnail,
                          String str_productsize, String str_image, String str_small_img, String str_Profile,
                          String artist_name, String str_category, String str_productid,
                          String pricers, String priceus, boolean is_front, String estamiate,
                          String productsize, String productdate, String reference,
                          String DollarRate, String Auction_id, String str_collectors) {
        this.str_title = str_title;
        this.str_description = str_description;
        this.str_artistid = str_artistid;
        this.str_thumbnail = str_thumbnail;
        this.str_productsize = str_productsize;
        this.str_image = str_image;
        this.str_small_img = str_small_img;
        this.str_Profile = str_Profile;
        this.is_front=is_front;
        this.artist_name = artist_name;

        this.str_category = str_category;
        this.str_productid = str_productid;
        this.priceus = priceus;
        this.pricers = pricers;
        this.estamiate=estamiate;
        this.productsize=productsize;
        this.productdate=productdate;
this.reference =reference;

        this.DollarRate = DollarRate;

        this.Auction_id=Auction_id;
        this.str_collectors=str_collectors;
    }

    String  DollarRate,pricers,priceus,str_productid,  str_category,  artist_name,  str_Profile,  str_small_img,  str_productsize,  str_image,  str_thumbnail,  str_artistid,  str_description,  str_title;
    private boolean is_front;

    String estamiate;

    public String getProductsize() {
        return productsize;
    }

    public void setProductsize(String productsize) {
        this.productsize = productsize;
    }

    public String getEstamiate() {
        return estamiate;
    }

    public void setEstamiate(String estamiate) {
        this.estamiate = estamiate;
    }

    public String getProductdate() {
        return productdate;
    }

    public void setProductdate(String productdate) {
        this.productdate = productdate;
    }

    String productsize;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    String reference;
    String productdate;

    public String getAuction_id() {
        return Auction_id;
    }

    public void setAuction_id(String auction_id) {
        Auction_id = auction_id;
    }

    String Auction_id,str_collectors;
}
