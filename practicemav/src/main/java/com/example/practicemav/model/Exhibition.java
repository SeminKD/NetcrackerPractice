package com.example.practicemav.model;

import javax.persistence.*;

@Entity
@Table(name = "exhibitions")
public class Exhibition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ex_id")
    private Long ex_id;

    @Column(name = "ex_name")
    private String ex_name;

    @Column(name = "ex_price")
    private double ex_price;

    public Exhibition(){}

    public Long getEx_id() {
        return ex_id;
    }

    public void setEx_id(Long ex_id) {
        this.ex_id = ex_id;
    }

    public String getEx_name() {
        return ex_name;
    }

    public void setEx_name(String ex_name) {
        this.ex_name = ex_name;
    }

    public double getEx_price() {
        return ex_price;
    }

    public void setEx_price(double ex_price) {
        this.ex_price = ex_price;
    }
}
