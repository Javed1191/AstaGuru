package com.infomanav.astaguru;

/**
 * Created by android-javed on 19-10-2016.
 */

public class Upcoming_Model {

    public String getStr_auction() {
        return str_auction;
    }

    public void setStr_auction(String str_auction) {
        this.str_auction = str_auction;
    }

    public String getStr_date() {
        return str_date;
    }

    public void setStr_date(String str_date) {
        this.str_date = str_date;
    }

    public Upcoming_Model(String str_auction, String str_date,String AuctionId,String Auctionname,String image) {
        this.str_auction = str_auction;
        this.str_date = str_date;
        this.AuctionId=AuctionId;
        this.Auctionname=Auctionname;
        this.image=image;
    }

    String str_auction,str_date;
    String AuctionId;

    public String getAuctionname() {
        return Auctionname;
    }

    public void setAuctionname(String auctionname) {
        Auctionname = auctionname;
    }

    public String getAuctionId() {
        return AuctionId;
    }

    public void setAuctionId(String auctionId) {
        AuctionId = auctionId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String Auctionname;
    String image;
}
