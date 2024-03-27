package com.example.chooseimage;

import java.io.Serializable;

public class ImageTitle implements Serializable {
    int id;
    String image, titleimg, author, category;

    public ImageTitle(int id, String image, String titleimg, String author, String category) {
        this.id = id;
        this.image = image;
        this.titleimg = titleimg;
        this.author = author;
        this.category = category;
    }

    public ImageTitle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitleimg() {
        return titleimg;
    }

    public void setTitleimg(String titleimg) {
        this.titleimg = titleimg;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
