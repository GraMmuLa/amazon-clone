package com.example.amazonclone.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="product_cards")
public class ProductCard {
    @Id
    @Column(name="id")
    private Long id;

    @Column(name="price")
    private Double price;

    @Column(name="from_who")
    private String fromWho;

    @Column(name="email")
    private String email;

    @Column(name="message")
    private String message;

    @Column(name="description")
    private String description;

    @Column(name="created_at")
    private Timestamp createdAt;
}
