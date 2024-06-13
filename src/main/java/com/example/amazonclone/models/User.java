package com.example.amazonclone.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="firstname", nullable = false)
    private String firstname;

    @Column(name="surname", nullable = false)
    private String surname;

    @Column(name="middlename")
    private String middlename;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="phone", nullable = false, unique = true)
    private String phone;

    @OneToOne(mappedBy = "user")
    private Banner banner;

    @OneToMany(mappedBy = "user")
    private Collection<Product> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name="users_favourite_product_colors",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="product_color_id")
    )
    private Collection<ProductColor> favouriteProductColors;

    @Column(name= "created_at")
    private Timestamp createdAt;
}
