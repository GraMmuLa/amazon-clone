package com.example.amazonclone.repos;

import com.example.amazonclone.models.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends RefreshableRepository<Review, Long> {
}
