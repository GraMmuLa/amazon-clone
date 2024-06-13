package com.example.amazonclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="category_images")
public class CategoryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="image")
    @Lob
    private byte[] image;

    @OneToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", unique = true)
    private Category category;

    @Column(name= "created_at")
    private Timestamp createdAt;
}
