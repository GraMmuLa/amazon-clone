package com.example.amazonclone.repos;

import com.example.amazonclone.models.ProductReview;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends RefreshableRepository<ProductReview, Long> {
}
