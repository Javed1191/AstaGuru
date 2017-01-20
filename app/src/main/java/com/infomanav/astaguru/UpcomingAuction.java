package com.infomanav.astaguru;

public class UpcomingAuction {

    private String app_id;

    private String app_title;
    private String app_logo;
    private String app_link;
    private String package_name;




    /*public PastAuction(String app_id, String app_title, String app_logo, String app_link, String package_name)
    {
        this.app_id = app_id;
        this.app_title = app_title;
        this.app_logo = app_logo;
        this.app_link = app_link;
        this.package_name=package_name;
    }*/

    public UpcomingAuction(String app_title, String app_logo)
    {
        this.app_title = app_title;
        this.app_logo = app_logo;
    }

    public String getAppId() {
        return app_id;
    }

    public void setAppId(String app_id) {
        this.app_id = app_id;
    }

    public String getAppTitle() {
        return app_title;
    }

    public void setAppTitle(String app_title) {
        this.app_title = app_title;
    }

    public String getAppLogo() {
        return app_logo;
    }

    public void setAppLogo(String app_logo) {
        this.app_logo = app_logo;
    }

    public String getAppLink() {
        return app_link;
    }

    public void setAppLink(String app_link) {
        this.app_link = app_link;
    }

    public String getAppPackage() {
        return package_name;
    }

    public void setAppPackege(String package_name) {
        this.package_name = package_name;
    }


}