package com.example.amazonclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name="product_types")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="name", length = 64)
    private String name;

    @ManyToOne
    @JoinColumn(name="subcategory_id")
    private Subcategory subcategory;

    @OneToMany(mappedBy = "productType")
    private Collection<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "productType")
    private Collection<ProductDetailKey> productDetailKeys = new ArrayList<>();

    @Column(name= "created_at")
    private Timestamp createdAt;
}
