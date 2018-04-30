package com.distributed.prject.distributedweb.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Coupon {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    int id;

    String name;
    String description;

    @ManyToMany(mappedBy = "coupons", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Set<User> users;

    public Coupon(String name, String description, Set<User> users, Set<Product> products) {
        this.name = name;
        this.description = description;
        this.users = users;
        this.products = products;
    }

    public Coupon() {
    }

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "product_coupon", joinColumns = {@JoinColumn(name = "coupon_id",
            referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "product_id",
            referencedColumnName = "id")})
    private Set<Product> products;


    public int getId() {
        return id;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
