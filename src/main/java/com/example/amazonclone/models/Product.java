package com.example.amazonclone.models;

import jakarta.persistence.*;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @ManyToOne
    @JoinColumn(name= "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "product")
    private Collection<ProductDetailValue> productDetailValues = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private Collection<ProductColor> productColors = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private Collection<Review> reviews = new ArrayList<>();

    @Column(name= "created_at")
    private Timestamp createdAt;
}
