package com.example.amazonclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name="banners")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="image")
    private byte[] image;

    @OneToOne
    @JoinColumn(name= "user_id")
    private User user;

    @Column(name="created_at")
    private Timestamp createdAt;
}
