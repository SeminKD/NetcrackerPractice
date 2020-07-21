package com.example.practicemav.model;

public class Queries {
    private Long order_id;
    private String order_time;
    private String ex_name;
    private int amount;
    private double order_price;

    public Queries(Long order_id, String order_time, String ex_name, int amount, double order_price) {
        this.order_id = order_id;
        this.order_time = order_time;
        this.ex_name = ex_name;
        this.amount = amount;
        this.order_price = order_price;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getEx_name() {
        return ex_name;
    }

    public void setEx_name(String ex_name) {
        this.ex_name = ex_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }
}
