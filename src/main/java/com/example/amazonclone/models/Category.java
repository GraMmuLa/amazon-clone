package com.example.amazonclone.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Nullable
    private Collection<Subcategory> subcategories = new ArrayList<>();

    @OneToOne(mappedBy = "category", cascade = CascadeType.REMOVE)
    @Nullable
    private CategoryImage image;

    @Column(name= "created_at")
    private Timestamp createdAt;
}
