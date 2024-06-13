package com.example.amazonclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name= "product_detail_keys")
public class ProductDetailKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name= "`key`", length = 64)
    private String key;

    @ManyToOne
    @JoinColumn(name="product_type_id")
    private ProductType productType;

    @OneToMany(mappedBy = "productDetailKey")
    private Collection<ProductDetailValue> productDetailValues = new ArrayList<>();

    @Column(name= "created_at")
    private Timestamp createdAt;
}
