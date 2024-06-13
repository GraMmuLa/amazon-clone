package com.example.amazonclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name="product_color_images")
public class ProductColorImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="image")
    @Lob
    private byte[] image;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="product_color_id")
    private ProductColor productColor;

    @Column(name= "created_at")
    private Timestamp createdAt;
}
