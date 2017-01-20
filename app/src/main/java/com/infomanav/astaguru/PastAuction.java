package com.infomanav.astaguru;

public class PastAuction {

    private String AuctionId;

    private String Auctionname;
    private boolean is_front;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAuctiontitle() {
        return auctiontitle;
    }

    public void setAuctiontitle(String auctiontitle) {
        this.auctiontitle = auctiontitle;
    }

    public String getAuctiondate() {
        return auctiondate;
    }

    public void setAuctiondate(String auctiondate) {
        this.auctiondate = auctiondate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDollarRate() {
        return DollarRate;
    }

    public void setDollarRate(String dollarRate) {
        DollarRate = dollarRate;
    }

    public String getAuctionId() {
        return AuctionId;
    }

    public void setAuctionId(String auctionId) {
        AuctionId = auctionId;
    }

    public String getAuctionname() {
        return Auctionname;
    }

    public void setAuctionname(String auctionname) {
        Auctionname = auctionname;
    }

    private String Date;

    public boolean is_front() {
        return is_front;
    }

    public void setIs_front(boolean is_front) {
        this.is_front = is_front;
    }

    public PastAuction(String auctionId, String auctiontitle, String auctiondate, String image, String dollarRate, String date, String  auctionname, String totalSaleValueRs, String totalSaleValueUs, boolean is_front) {
        AuctionId = auctionId;
        this.auctiontitle = auctiontitle;
        this.auctiondate = auctiondate;
        this.image = image;
        DollarRate = dollarRate;
        Date = date;
        Auctionname = auctionname;
        this.totalSaleValueRs=totalSaleValueRs;
                this.totalSaleValueUs=totalSaleValueUs;
        this.is_front=is_front;
    }

    private String DollarRate;
    private String image;
    private String auctiondate;
    private String auctiontitle;

    public String getTotalSaleValueRs() {
        return totalSaleValueRs;
    }

    public void setTotalSaleValueRs(String totalSaleValueRs) {
        this.totalSaleValueRs = totalSaleValueRs;
    }

    public String getTotalSaleValueUs() {
        return totalSaleValueUs;
    }

    public void setTotalSaleValueUs(String totalSaleValueUs) {
        this.totalSaleValueUs = totalSaleValueUs;
    }

    private String totalSaleValueRs;
    private String totalSaleValueUs;







}