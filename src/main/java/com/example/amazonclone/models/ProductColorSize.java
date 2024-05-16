package com.example.amazonclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name="product_color_sizes")
public class ProductColorSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="product_color_id", nullable = false)
    private Long productColorId;

    @Column(name = "size_id", nullable = false)
    private Long productSizeId;

    @Column(name= "created_at")
    private Timestamp createdAt;
}
