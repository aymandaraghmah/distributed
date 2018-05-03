package com.distributed.prject.distributedweb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.util.List;

@Entity
public class FoodCategory implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String name;
    String picture;

    public FoodCategory(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }

    public FoodCategory() {
    }

    @OneToMany( fetch = FetchType.LAZY, cascade = {CascadeType.ALL},mappedBy="category")
    List<Product> products;

    @JsonIgnore
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}