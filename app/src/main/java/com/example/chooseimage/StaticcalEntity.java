package com.example.chooseimage;

public class StaticcalEntity {
    private Integer id;
    private Integer book_id;
    private Integer month;
    private Integer total;

    public StaticcalEntity() {
    }

    public StaticcalEntity(Integer id, Integer name, Integer month, Integer total) {
        this.id = id;
        this.book_id = name;
        this.month = month;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getName() {
        return book_id;
    }

    public void setName(Integer name) {
        this.book_id = name;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
