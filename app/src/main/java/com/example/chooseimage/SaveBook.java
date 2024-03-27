package com.example.chooseimage;

public class SaveBook {
    int id, user_id, book_id;

    public SaveBook(int id, int user_id, int book_id) {
        this.id = id;
        this.user_id = user_id;
        this.book_id = book_id;
    }

    public SaveBook() {
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

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }
}
