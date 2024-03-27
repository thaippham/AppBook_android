package com.example.chooseimage;

public class SaveChap {
    int id, user_id, chapter_id;

    public SaveChap(int id, int user_id, int chapter_id) {
        this.id = id;
        this.user_id = user_id;
        this.chapter_id = chapter_id;
    }

    public SaveChap() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }
}
