package com.example.amazonclone.repos;

import com.example.amazonclone.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends RefreshableRepository<Product, Long> {
    public Optional<List<Product>> findAllByUserId(Long userId);
}