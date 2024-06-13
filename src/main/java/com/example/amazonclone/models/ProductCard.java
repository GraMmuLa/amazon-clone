package com.example.amazonclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="product_cards")
public class ProductCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="code")
    private String code;

    @Column(name="price")
    private Double price;

    @Column(name="from_who")
    private String fromWho;

    @Column(name="email")
    private String email;

    @Column(name="message")
    private String message;

    @Column(name="created_at")
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name="product_card_design_id")
    private ProductCardDesign productCardDesign;
}
