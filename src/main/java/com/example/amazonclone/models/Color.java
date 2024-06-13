package com.example.amazonclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="colors")
@Data
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="color")
    private String color;

    @ManyToOne
    @JoinColumn(name="subcategory_id")
    private Subcategory subcategory;

    @OneToMany(mappedBy = "color")
    private List<ProductColor> productColors = new ArrayList<>();

    @Column(name= "created_at")
    private Timestamp createdAt;
}
