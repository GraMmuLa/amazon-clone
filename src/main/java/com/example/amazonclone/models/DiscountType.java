package com.example.amazonclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name="discount_types")
public class DiscountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "discountType")
    private List<Discount> discounts;

    @Column(name= "created_at")
    private Timestamp createdAt;
}
