package com.example.amazonclone.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name="subcategories")
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(mappedBy = "subcategory", fetch = FetchType.EAGER)
    private Collection<ProductType> productTypes = new ArrayList<>();

    @OneToMany(mappedBy = "subcategory", fetch = FetchType.EAGER)
    private Collection<Color> colors = new ArrayList<>();

    @OneToOne(mappedBy = "subcategory", cascade = CascadeType.REMOVE)
    @Nullable
    private SubcategoryImage subcategoryImage;

    @Column(name= "created_at")
    private Timestamp createdAt;
}
