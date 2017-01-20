package com.infomanav.astaguru;


public class ItemSpecialist {

    private String title,post,emailid;
    private int photo;

    public ItemSpecialist(String title, String post, int photo, String emailid) {
        this.title = title;
        this.photo = photo;
        this.post = post;
        this.emailid =emailid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = title;
    }
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getEmmailId() {
        return emailid;
    }

    public void setEmailId(String emailid) {
        this.emailid = emailid;
    }
}
