package com.example.amazonclone.services;

import com.example.amazonclone.dto.ProductCardDesignDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.ProductCardDesign;
import com.example.amazonclone.repos.ProductCardDesignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductCardDesignService implements JpaService<ProductCardDesignDto, ProductCardDesign, Long> {

    private final ProductCardDesignRepository productCardDesignRepository;

    private ProductCardDesign getProductCardDesign(Long id) throws NotFoundException {
        return productCardDesignRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Product card design was not found!"));
    }

    @Override
    public ProductCardDesignDto get(Long id) throws NotFoundException {
        return new ProductCardDesignDto(productCardDesignRepository.findById(id).orElseThrow(()->new NotFoundException("Gift card was not found!")));
    }

    @Override
    public List<ProductCardDesignDto> getAll(PageRequest pageRequest) {
        List<ProductCardDesignDto> productCardDesignDtos = new ArrayList<>();

        Page<ProductCardDesign> giftCardPage = productCardDesignRepository.findAll(pageRequest);

        giftCardPage.getContent().forEach(productCardDesign -> productCardDesignDtos.add(new ProductCardDesignDto(productCardDesign)));

        return productCardDesignDtos;
    }

    @Override
    public List<ProductCardDesignDto> getAll() {
        List<ProductCardDesignDto> productCardDesignDtos = new ArrayList<>();

        productCardDesignRepository.findAll().forEach(productCardDesign -> productCardDesignDtos.add(new ProductCardDesignDto(productCardDesign)));;

        return productCardDesignDtos;
    }

    @Override
    public ProductCardDesignDto getLast() {
        List<ProductCardDesignDto> productCardDesignDtos = getAll();
        return productCardDesignDtos.get(productCardDesignDtos.size()-1);
    }

    @Override
    public ProductCardDesignDto add(ProductCardDesignDto dtoEntity) {

        ProductCardDesign productCardDesign = dtoEntity.buildEntity();

        productCardDesignRepository.saveAndFlush(productCardDesign);
        productCardDesignRepository.refresh(productCardDesign);

        return getLast();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        productCardDesignRepository.delete(getProductCardDesign(id));
    }

    @Override
    public ProductCardDesignDto update(Long id, ProductCardDesignDto dtoEntity) throws NotFoundException {
        delete(id);

        ProductCardDesign productCardDesign = dtoEntity.buildEntity(id);

        productCardDesignRepository.saveAndFlush(productCardDesign);
        productCardDesignRepository.refresh(productCardDesign);

        return getLast();
    }
}
