package com.example.chooseimage;

public class Chapter {
    int id, book_id, chapter, views;
    String content;

    public Chapter(int id, int book_id, int chapter, int views, String content) {
        this.id = id;
        this.book_id = book_id;
        this.chapter = chapter;
        this.views = views;
        this.content = content;
    }

    public Chapter() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
