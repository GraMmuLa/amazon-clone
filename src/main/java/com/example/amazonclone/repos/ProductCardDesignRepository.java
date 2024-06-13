package com.example.amazonclone.repos;

import com.example.amazonclone.models.ProductCardDesign;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCardDesignRepository extends RefreshableRepository<ProductCardDesign, Long> {
}
