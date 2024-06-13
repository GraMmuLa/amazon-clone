package com.example.amazonclone.services;

import com.example.amazonclone.dto.ProductColorSizeDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.ProductColor;
import com.example.amazonclone.models.ProductColorSize;
import com.example.amazonclone.repos.ProductColorRepository;
import com.example.amazonclone.repos.ProductColorSizeRepository;
import com.example.amazonclone.repos.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO
@Service
public class ProductColorSizeService implements JpaService<ProductColorSizeDto, ProductColorSize, Long> {

    private final ProductColorSizeRepository productColorSizeRepository;
    private final ProductColorRepository productColorRepository;
    private final ProductSizeRepository productSizeRepository;

    @Autowired
    public ProductColorSizeService(ProductColorSizeRepository productColorSizeRepository,
                                   ProductColorRepository productColorRepository,
                                   ProductSizeRepository productSizeRepository) {
        this.productColorSizeRepository = productColorSizeRepository;
        this.productColorRepository = productColorRepository;
        this.productSizeRepository = productSizeRepository;
    }

    @Override
    public ProductColorSizeDto get(Long id) throws NotFoundException {
        Optional<ProductColorSize> productColorSize = productColorSizeRepository.findById(id);
        if(productColorSize.isPresent())
            return new ProductColorSizeDto(productColorSize.get());
        throw new NotFoundException("Product color size was not found!");
    }

    @Override
    public List<ProductColorSizeDto> getAll(PageRequest pageRequest) {

        List<ProductColorSizeDto> productColorSizeDtos = new ArrayList<>();

        productColorSizeRepository.findAll(pageRequest).getContent().forEach(productColorSize ->
                productColorSizeDtos.add(new ProductColorSizeDto(productColorSize)));

        return productColorSizeDtos;
    }

    @Override
    public List<ProductColorSizeDto> getAll() {
        List<ProductColorSizeDto> productColorSizeDtos = new ArrayList<>();

        productColorSizeRepository.findAll().forEach(productColorSize ->
                productColorSizeDtos.add(new ProductColorSizeDto(productColorSize)));

        return productColorSizeDtos;
    }

    public int getSize() {
        return productColorSizeRepository.findAll().size();
    }

    @Override
    public ProductColorSizeDto getLast() {
        List<ProductColorSizeDto> productColorSizeDtos = getAll();
        return productColorSizeDtos.get(productColorSizeDtos.size()-1);
    }

    @Override
    public ProductColorSizeDto add(ProductColorSizeDto dtoEntity) throws NotFoundException {

        ProductColorSize productColorSize = dtoEntity.buildEntity();

        if(!productColorRepository.existsById(dtoEntity.getProductColorId()))
            throw new NotFoundException("Product color not found!");
        if(!productSizeRepository.existsById(dtoEntity.getProductSizeId()))
            throw new NotFoundException("Product size not found!");

        productColorSizeRepository.saveAndFlush(productColorSize);
        productColorSizeRepository.refresh(productColorSize);

        return new ProductColorSizeDto(productColorSize);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if(!productColorSizeRepository.existsById(id))
            throw new NotFoundException("Product color size was not found!");
        productColorSizeRepository.deleteById(id);
    }

    @Override
    public ProductColorSizeDto update(Long id, ProductColorSizeDto dtoEntity) throws NotFoundException {
        delete(id);

        dtoEntity.setId(id);

        add(dtoEntity);

        return getLast();
    }
}
