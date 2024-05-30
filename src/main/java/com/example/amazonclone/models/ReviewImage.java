package com.example.amazonclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="reviews_images")
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="image")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name="review_id")
    private Review review;

    @Column(name="created_at")
    private Timestamp createdAt;
}
