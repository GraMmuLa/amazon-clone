package com.example.amazonclone.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name="product_colors")
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(mappedBy = "productColor", cascade = CascadeType.REMOVE)
    @Nullable
    private Discount discount;

    @OneToOne
    @JoinColumn(name="main_image_id", nullable = true, unique = true)
    @Nullable
    private ProductColorImage mainImage;

    @OneToMany(mappedBy = "productColor", cascade = CascadeType.REMOVE)
    private Collection<ProductColorImage> productColorImages = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name="product_color_sizes",
            joinColumns = @JoinColumn(name="product_color_id"),
            inverseJoinColumns = @JoinColumn(name= "size_id"))
    private Collection<ProductSize> productSizes = new ArrayList<>();

    @ManyToMany(mappedBy = "favouriteProductColors")
    private Collection<User> favouritedUsers;

    @Column(name= "created_at")
    private Timestamp createdAt;
}
