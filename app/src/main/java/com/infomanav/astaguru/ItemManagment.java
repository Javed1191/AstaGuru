package com.infomanav.astaguru;


public class ItemManagment {

    private String title,post;
    private String photo;

    public ItemManagment(String title,String post, String photo) {
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
