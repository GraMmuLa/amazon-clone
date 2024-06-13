package com.example.amazonclone.services;

import com.example.amazonclone.dto.ReviewImageDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.Review;
import com.example.amazonclone.models.ReviewImage;
import com.example.amazonclone.repos.ReviewImageRepository;
import com.example.amazonclone.repos.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewImageService implements JpaService<ReviewImageDto, ReviewImage, Long> {
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewImageDto get(Long id) throws NotFoundException, IOException {
        return new ReviewImageDto(reviewImageRepository.findById(id).orElseThrow(()->
                new NotFoundException("Review image was not found!")));
    }

    @Override
    public List<ReviewImageDto> getAll(PageRequest pageRequest) {
        List<ReviewImageDto> reviewImageDtos = new ArrayList<>();

        Page<ReviewImage> page = reviewImageRepository.findAll(pageRequest);
        page.getContent().forEach(reviewImage -> reviewImageDtos.add(new ReviewImageDto(reviewImage).deflateImage()));

        return reviewImageDtos;
    }

    @Override
    public List<ReviewImageDto> getAll() {
        List<ReviewImageDto> reviewImageDtos = new ArrayList<>();

        reviewImageRepository.findAll().forEach(reviewImage -> reviewImageDtos.add(new ReviewImageDto(reviewImage).deflateImage()));

        return reviewImageDtos;
    }

    @Override
    public ReviewImageDto getLast() {
        List<ReviewImageDto> reviewImageDtos = getAll();
        return reviewImageDtos.get(reviewImageDtos.size()-1);
    }

    @Override
    public ReviewImageDto add(ReviewImageDto dtoEntity) throws NotFoundException {

        ReviewImage reviewImage = dtoEntity.buildEntity();

        reviewImage.setReview(reviewRepository.findById(dtoEntity.getReviewId())
                .orElseThrow(() -> new NotFoundException("Review was not found!")));

        reviewImageRepository.saveAndFlush(reviewImage);
        reviewImageRepository.refresh(reviewImage);

        return getLast();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if(!reviewImageRepository.existsById(id))
            throw new NotFoundException("Review image was not found!");

        reviewImageRepository.deleteById(id);

    }

    @Override
    public ReviewImageDto update(Long id, ReviewImageDto dtoEntity) throws NotFoundException {
        delete(id);

        ReviewImage reviewImage = dtoEntity.buildEntity(id);

        reviewImageRepository.saveAndFlush(reviewImage);
        reviewImageRepository.refresh(reviewImage);

        return dtoEntity;
    }
}
