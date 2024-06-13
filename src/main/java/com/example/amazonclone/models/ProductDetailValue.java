package com.example.amazonclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name="product_detail_values")
public class ProductDetailValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "product_detail_key_id")
    private ProductDetailKey productDetailKey;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name= "created_at")
    private Timestamp createdAt;
}
