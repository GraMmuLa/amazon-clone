package com.example.amazonclone.services;

import com.example.amazonclone.dto.ReviewDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.Product;
import com.example.amazonclone.models.ProductReview;
import com.example.amazonclone.models.User;
import com.example.amazonclone.repos.ProductRepository;
import com.example.amazonclone.repos.ReviewRepository;
import com.example.amazonclone.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements JpaService<ReviewDto, ProductReview, Long> {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private ProductReview getProductReview(Long id) throws NotFoundException {
        for(ProductReview productReview : reviewRepository.findAll())
            if(productReview.getId().equals(id))
                return productReview;
        throw new NotFoundException("Product review was not found!");
    }

    @Override
    public ReviewDto get(Long id) throws NotFoundException {
        return new ReviewDto(getProductReview(id));
    }

    @Override
    public List<ReviewDto> getAll(PageRequest pageRequest) {
        List<ReviewDto> reviewDtos = new ArrayList<>();
        Page<ProductReview> page = reviewRepository.findAll(pageRequest);

        page.getContent().forEach(x-> reviewDtos.add(new ReviewDto(x)));

        return reviewDtos;
    }

    @Override
    public List<ReviewDto> getAll() {
        List<ReviewDto> reviewDtos = new ArrayList<>();

        reviewRepository.findAll().forEach(x-> reviewDtos.add(new ReviewDto(x)));

        return reviewDtos;
    }

    public int getSize() {
        return reviewRepository.findAll().size();
    }

    @Override
    public ReviewDto getLast() {
        return getAll().get(getAll().size()-1);
    }

    @Override
    public ReviewDto add(ReviewDto dtoEntity) throws NotFoundException {
        ProductReview productReview = dtoEntity.buildEntity();
        for (Product product : productRepository.findAll())
            if(dtoEntity.getProductId().equals(product.getId()))
                productReview.setProduct(product);
        if(productReview.getProduct() == null)
            throw new NotFoundException("Product was not found!");

        for (User user : userRepository.findAll())
            if(dtoEntity.getProductId().equals(user.getId()))
                productReview.setUser(user);
        if(productReview.getProduct() == null)
            throw new NotFoundException("User was not found!");

        reviewRepository.saveAndFlush(productReview);
        reviewRepository.refresh(productReview);

        return getLast();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        reviewRepository.delete(getProductReview(id));
    }

    @Override
    public ReviewDto update(Long id, ReviewDto dtoEntity) throws NotFoundException {
        delete(id);

        ProductReview productReview = dtoEntity.buildEntity(id);

        reviewRepository.saveAndFlush(productReview);
        reviewRepository.refresh(productReview);

        return getLast();
    }
}
