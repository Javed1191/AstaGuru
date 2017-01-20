package com.infomanav.astaguru;

/**
 * Created by android-javed on 26-10-2016.
 */

public class Current_Auction_Model {

    private  String str_productid;
    private   String str_title;

    private ItemFace mItemFace;

    public enum ItemFace{
        FRONT,
        BACK
    }
    private boolean is_front;


    private  String str_description;
    private  String str_artistid;
    private  String str_thumbnail;
    private  String str_image;
    private   String str_productsize;
    private   String str_small_img;
    private   String str_Profile;
    private  String artist_name;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
    public ItemFace getFace(){
        return mItemFace;
    }

    public void setFace(ItemFace face){
        mItemFace = face;
    }
    private  String str_category,reference;

    public String getDollarRate() {
        return DollarRate;
    }

    public void setDollarRate(String dollarRate) {
        DollarRate = dollarRate;
    }

    public String getPriceus() {
        return priceus;
    }

    public void setPriceus(String priceus) {
        this.priceus = priceus;
    }

    public String getPricers() {
        return pricers;
    }

    public void setPricers(String pricers) {
        this.pricers = pricers;
    }

    private  String pricers;
    private  String priceus;
    private String medium;
    private  String MyUserID;
    private String str_collectors,currentDate;

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

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    private String productsize;
    private String estamiate,DollarRate;

    public String getStr_Bidclosingtime() {
        return str_Bidclosingtime;
    }

    public void setStr_Bidclosingtime(String str_Bidclosingtime) {
        this.str_Bidclosingtime = str_Bidclosingtime;
    }

    private String str_Bidclosingtime,productdate;

    public String getMyUserID() {
        return MyUserID;
    }

    public void setMyUserID(String myUserID) {
        MyUserID = myUserID;
    }

    public String getStr_collectors() {
        return str_collectors;
    }

    public void setStr_collectors(String str_collectors) {
        this.str_collectors = str_collectors;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public Current_Auction_Model(String str_productid, String str_category, String artist_name, String str_Profile, String str_small_img,
                                 String str_productsize, String str_image, String str_thumbnail,
                                 String str_artistid, String str_description, String str_title, String str_Bidclosingtime,
                                 boolean is_front, String pricers, String priceus, String medium, String productsize,
                                 String estamiate, String DollarRate, String reference, String productdate, String MyUserID, String str_collectors, String currentDate) {
        this.str_productid = str_productid;

        this.str_category = str_category;
        this.artist_name = artist_name;
        this.str_Profile = str_Profile;
        this.str_small_img = str_small_img;
        this.str_productsize = str_productsize;
        this.str_image = str_image;
        this.str_thumbnail = str_thumbnail;
        this.str_artistid = str_artistid;

        this.str_description = str_description;
        this.str_title = str_title;
        this.is_front=is_front;
        this.pricers = pricers;
        this.priceus=priceus;
        this.str_Bidclosingtime=str_Bidclosingtime;
        this.medium=medium;
        this.productsize=productsize;
        this.estamiate=estamiate;
        this.DollarRate=DollarRate;

        this.reference=reference;
        this.productdate=productdate;
this.MyUserID=MyUserID;
        mItemFace = ItemFace.FRONT;
        this.str_collectors=str_collectors;
        this.currentDate=currentDate;
    }
    public String getStr_productid() {
        return str_productid;
    }

    public String getProductdate() {
        return productdate;
    }

    public void setProductdate(String productdate) {
        this.productdate = productdate;
    }

    public void setStr_productid(String str_productid) {
        this.str_productid = str_productid;
    }

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

    public String getStr_image() {
        return str_image;
    }

    public void setStr_image(String str_image) {
        this.str_image = str_image;
    }

    public String getStr_thumbnail() {
        return str_thumbnail;
    }

    public void setStr_thumbnail(String str_thumbnail) {
        this.str_thumbnail = str_thumbnail;
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

    public boolean getIs_front() {
        return is_front;
    }

    public void setIs_front(boolean is_front) {
        this.is_front = is_front;
    }


}
