package com.example.amazonclone.services;

import com.example.amazonclone.dto.ProductColorImageDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.ProductColor;
import com.example.amazonclone.models.ProductColorImage;
import com.example.amazonclone.repos.ProductColorImageRepository;
import com.example.amazonclone.repos.ProductColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProductColorImageService implements JpaService<ProductColorImageDto, ProductColorImage, Long> {

    private final ProductColorImageRepository productColorImageRepository;
    private final ProductColorRepository productColorRepository;

    @Autowired
    public ProductColorImageService(ProductColorImageRepository productImageRepository, ProductColorRepository productColorRepository) {
        this.productColorImageRepository = productImageRepository;
        this.productColorRepository = productColorRepository;
    }

    private ProductColorImage getImage(Long id) throws NotFoundException {
        Iterable<ProductColorImage> productImages = productColorImageRepository.findAll();

        for (ProductColorImage productColorImage : productImages) {
            if(productColorImage.getId().equals(id))
                return productColorImage;
        }
        throw new NotFoundException("Product image was not found");
    }

    @Override
    public ProductColorImageDto get(Long id) throws NotFoundException {
        return new ProductColorImageDto(getImage(id));
    }

    @Override
    public List<ProductColorImageDto> getAll(PageRequest pageRequest) {
        List<ProductColorImageDto> productImageDtos = new LinkedList<>();
        Page<ProductColorImage> page = productColorImageRepository.findAll(pageRequest);

        page.getContent().forEach(x->productImageDtos.add(new ProductColorImageDto(x).deflateImage()));

        return productImageDtos;
    }

    @Override
    public List<ProductColorImageDto> getAll() {
        List<ProductColorImageDto> productImageDtos = new LinkedList<>();

        productColorImageRepository.findAll().forEach(x->productImageDtos.add(new ProductColorImageDto(x).deflateImage()));

        return productImageDtos;
    }

    public int getSize() {
        return productColorImageRepository.findAll().size();
    }

    public List<ProductColorImageDto> getAllByProductColorId(Long productColorId) throws NotFoundException {

        if(!productColorRepository.existsById(productColorId))
            throw new NotFoundException("Product Color was not found!");

        List<ProductColorImageDto> productColorImageDtos = new ArrayList<>();

        for (ProductColorImage productColorImage : productColorImageRepository.findAll())
            if(productColorImage.getProductColor().getId().equals(productColorId))
                productColorImageDtos.add(new ProductColorImageDto(productColorImage).deflateImage());

        return productColorImageDtos;
    }

    @Override
    public ProductColorImageDto getLast() {
        return getAll().get(getAll().size()-1);
    }

    @Override
    public ProductColorImageDto add(ProductColorImageDto dtoEntity) throws NotFoundException {
        ProductColorImage productColorImage = dtoEntity.buildEntity();

        Iterable<ProductColor> productColors = productColorRepository.findAll();

        for (ProductColor productColor : productColors) {
            if(productColor.getId().equals(dtoEntity.getProductColorId()))
                productColorImage.setProductColor(productColor);
        }
        if(productColorImage.getProductColor() == null)
            throw new NotFoundException("Product Color was not found");

        productColorImageRepository.saveAndFlush(productColorImage);
        productColorImageRepository.refresh(productColorImage);

        return getLast();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        ProductColorImage image = getImage(id);

        productColorImageRepository.delete(image);
    }

    @Override
    public ProductColorImageDto update(Long id, ProductColorImageDto dtoEntity) throws NotFoundException {
        delete(id);

        ProductColorImage productColorImage = dtoEntity.buildEntity(id);

        productColorImageRepository.saveAndFlush(productColorImage);
        productColorImageRepository.refresh(productColorImage);

        return getLast();
    }
}
