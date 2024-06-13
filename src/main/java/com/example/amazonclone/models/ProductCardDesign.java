package com.example.amazonclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="product_card_designs")
public class ProductCardDesign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "productCardDesign")
    private List<ProductCard> productCards = new ArrayList<>();

    @OneToOne(mappedBy = "productCardDesign")
    private ProductCardDesignImage productCardDesignImage;
}
