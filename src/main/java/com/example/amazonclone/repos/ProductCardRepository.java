package com.example.amazonclone.repos;

import com.example.amazonclone.models.ProductCard;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCardRepository extends RefreshableRepository<ProductCard, Long> {
}
