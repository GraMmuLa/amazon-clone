package com.example.amazonclone.repos;

import com.example.amazonclone.models.ProductDetailValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailValueRepository extends RefreshableRepository<ProductDetailValue, Long> {
}
