package com.example.amazonclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="role_name", nullable = false, unique = true)
    private String roleName;

    @OneToMany(mappedBy = "role")
    private Collection<User> user = new ArrayList<>();

    @Column(name= "created_at")
    private Timestamp createdAt;
}
