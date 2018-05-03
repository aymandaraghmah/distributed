package com.distributed.prject.distributedweb.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    double price;
    String name;

    @CreationTimestamp
    @Column(insertable = true, updatable = false)
    private Date added;

    private double sale;

    private String image;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Coupon> coupons ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private FoodCategory category;

    public Product(double price, String name, Date added, double sale, String image, List<Coupon> coupons) {
        this.price = price;
        this.name = name;
        this.added = added;
        this.sale = sale;
        this.image = image;
        this.coupons = coupons;
    }

    public Product() {
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public FoodCategory getFoodCategory() {
        return category;
    }

    public void setFoodCategory(FoodCategory foodCategory) {
        this.category = foodCategory;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
