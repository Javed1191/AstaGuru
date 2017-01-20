package com.infomanav.astaguru;


public class ItemManagment {

    private String title,post;
    private int photo;

    public ItemManagment(String title,String post, int photo) {
        this.title = title;
        this.photo = photo;
        this.post = post;
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
}
