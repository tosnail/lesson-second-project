package com.example.lesson_second_project;

public class Item {
    private Long id;
    private String itname;
    private String itdate;
    private String itposition;
    private String itmemo;
    private Long ituser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItname() {
        return itname;
    }

    public void setItname(String itname) {
        this.itname = itname;
    }

    public String getItdate() {
        return itdate;
    }

    public void setItdate(String itdate) {
        this.itdate = itdate;
    }

    public String getItposition() {
        return itposition;
    }

    public void setItposition(String itposition) {
        this.itposition = itposition;
    }

    public String getItmemo() {
        return itmemo;
    }

    public void setItmemo(String itmemo) {
        this.itmemo = itmemo;
    }

    public Long getItuser() {
        return ituser;
    }

    public void setItuser(Long ituser) {
        this.ituser = ituser;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itname='" + itname + '\'' +
                ", itdate='" + itdate + '\'' +
                ", itposition='" + itposition + '\'' +
                ", itmemo='" + itmemo + '\'' +
                ", ituser=" + ituser +
                '}';
    }
}
