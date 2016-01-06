package com.hh.ehh.model;

/**
 * Created by mpifa on 2/1/16.
 */
public class News {
    private String title;
    private String body;
    private String image;

    public News(String title, String body, String image) {
        this.title = title;
        this.body = body;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
