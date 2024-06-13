package com.example.amazonclone.repos;

import com.example.amazonclone.models.ProductColorSize;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductColorSizeRepository extends RefreshableRepository<ProductColorSize,Long> {
}
