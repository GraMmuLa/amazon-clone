package com.example.amazonclone.dto;

import com.example.amazonclone.models.Review;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class ReviewDto implements DtoEntity<Review, Long> {

    @Nullable
    private Long id;

    private Double mark;

    @Nullable
    private String reviewText;

    private Long userId;

    private Long productId;

    private Timestamp createdAt;

    public ReviewDto(Review entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.mark = entity.getMark();
        this.reviewText = entity.getReviewText();
        this.productId = entity.getProduct().getId();
        this.createdAt = entity.getCreatedAt();
    }

    public ReviewDto(Double mark, Long userId, Long productId) {
        this.userId = userId;
        this.mark = mark;
        this.productId = productId;
    }

    public ReviewDto(Double mark, @Nullable String reviewText, Long userId, Long productId) {
        this(mark, productId, userId);
        this.reviewText = reviewText;
    }

    @Override
    public Review buildEntity() {
        Review review = new Review();

        if(id != null)
            review.setId(id);
        review.setMark(mark);
        if(reviewText != null)
            review.setReviewText(reviewText);

        return review;
    }

    @Override
    public Review buildEntity(Long id) {
        this.id = id;
        return buildEntity();
    }
}
