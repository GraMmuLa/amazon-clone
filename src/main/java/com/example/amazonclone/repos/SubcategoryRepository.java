package com.example.amazonclone.repos;

import com.example.amazonclone.models.Subcategory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubcategoryRepository extends RefreshableRepository<Subcategory, Long> {
    public Optional<Subcategory> findByName(String name);
}
