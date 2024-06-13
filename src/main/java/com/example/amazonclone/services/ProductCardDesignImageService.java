package com.example.amazonclone.services;

import com.example.amazonclone.dto.ProductCardDesignImageDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.ProductCardDesign;
import com.example.amazonclone.models.ProductCardDesignImage;
import com.example.amazonclone.repos.ProductCardDesignImageRepository;
import com.example.amazonclone.repos.ProductCardDesignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCardDesignImageService implements JpaService<ProductCardDesignImageDto, ProductCardDesignImage, Long> {
    private final ProductCardDesignImageRepository productCardDesignImageRepository;
    private final ProductCardDesignRepository productCardDesignRepository;

    @Override
    public ProductCardDesignImageDto get(Long id) throws NotFoundException, IOException {
        return new ProductCardDesignImageDto(productCardDesignImageRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Product card design image was not found!")));
    }

    @Override
    public List<ProductCardDesignImageDto> getAll(PageRequest pageRequest) {
        List<ProductCardDesignImageDto> productCardDesignImageDtos = new ArrayList<>();

        Page<ProductCardDesignImage> page = productCardDesignImageRepository.findAll(pageRequest);

        page.getContent().forEach(productCardDesignImage ->
                productCardDesignImageDtos.add(new ProductCardDesignImageDto(productCardDesignImage).deflateImage()));

        return productCardDesignImageDtos;
    }

    @Override
    public List<ProductCardDesignImageDto> getAll() {
        List<ProductCardDesignImageDto> productCardDesignImageDtos = new ArrayList<>();

        productCardDesignImageRepository.findAll().forEach(productCardDesignImage ->
                productCardDesignImageDtos.add(new ProductCardDesignImageDto(productCardDesignImage)));

        return productCardDesignImageDtos;
    }

    @Override
    public ProductCardDesignImageDto getLast() {
        List<ProductCardDesignImageDto> productCardDesignImageDtos = getAll();
        return productCardDesignImageDtos.get(productCardDesignImageDtos.size()-1);
    }

    @Override
    public ProductCardDesignImageDto add(ProductCardDesignImageDto dtoEntity) throws NotFoundException {

        ProductCardDesignImage productCardDesignImage = dtoEntity.buildEntity();

        for(ProductCardDesign productCardDesign : productCardDesignRepository.findAll())
            if(productCardDesign.getId().equals(dtoEntity.getProductCardDesignId()))
                productCardDesignImage.setProductCardDesign(productCardDesign);

        if(productCardDesignImage.getProductCardDesign() == null)
            throw new NotFoundException("Product card design was not found!");

        productCardDesignImageRepository.saveAndFlush(productCardDesignImage);
        productCardDesignImageRepository.refresh(productCardDesignImage);

        return getLast();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if(!productCardDesignImageRepository.existsById(id))
            throw new NotFoundException("Product card design image not found!");
        productCardDesignImageRepository.deleteById(id);
    }

    @Override
    public ProductCardDesignImageDto update(Long id, ProductCardDesignImageDto dtoEntity) throws NotFoundException {
        delete(id);

        ProductCardDesignImage productCardDesignImage = dtoEntity.buildEntity(id);
        productCardDesignImageRepository.saveAndFlush(productCardDesignImage);
        productCardDesignImageRepository.refresh(productCardDesignImage);

        return dtoEntity;
    }
}
