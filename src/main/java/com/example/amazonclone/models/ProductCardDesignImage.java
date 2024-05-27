package com.example.amazonclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="product_card_design_images")
public class ProductCardDesignImage{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="image")
    private byte[] image;

    @Column(name="created_at")
    private Timestamp createdAt;

    @OneToOne
    @JoinColumn(name="product_card_design_id")
    private ProductCardDesign productCardDesign;
}
