package com.example.amazonclone.dto;

import com.example.amazonclone.Image;
import com.example.amazonclone.models.ReviewImage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;

public class ReviewImageDto extends Image implements DtoEntity<ReviewImage, Long> {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private Long reviewId;
    @Getter
    @Setter
    private Timestamp createdAt;

    public ReviewImageDto(MultipartFile file, Long reviewId) throws IOException {
        super(file);
        this.reviewId = reviewId;
    }

    public ReviewImageDto(ReviewImage entity) {
        super(entity.getImage());
        this.id = entity.getId();
        this.reviewId = entity.getReview().getId();
        this.createdAt = entity.getCreatedAt();
    }

    @Override
    public ReviewImage buildEntity() {
        ReviewImage reviewImage = new ReviewImage();

        if(id != null)
            reviewImage.setId(id);
        reviewImage.setImage(data);

        return reviewImage;
    }

    @Override
    public ReviewImage buildEntity(Long id) {
        this.id = id;

        return buildEntity();
    }

    public ReviewImageDto deflateImage() {
        decompressImage();
        return this;
    }
}
