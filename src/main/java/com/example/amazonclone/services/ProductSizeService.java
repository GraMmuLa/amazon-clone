package com.example.amazonclone.services;

import com.example.amazonclone.dto.ProductSizeDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.ProductColor;
import com.example.amazonclone.models.ProductSize;
import com.example.amazonclone.repos.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSizeService implements JpaService<ProductSizeDto, ProductSize, Long> {

    private final ProductSizeRepository productSizeRepository;

    @Autowired
    public ProductSizeService(ProductSizeRepository productSizeRepository) {
        this.productSizeRepository = productSizeRepository;
    }

    private ProductSize getProductSize(Long id) throws NotFoundException {
        for(ProductSize productSize : productSizeRepository.findAll())
            if(productSize.getId().equals(id))
                return productSize;
        throw new NotFoundException("Product size was not found!");
    }

    @Override
    public ProductSizeDto get(Long id) throws NotFoundException {
        return new ProductSizeDto(getProductSize(id));
    }

    @Override
    public List<ProductSizeDto> getAll(PageRequest pageRequest) {
        List<ProductSizeDto> productSizeDtos = new ArrayList<>();
        Page<ProductSize> page = productSizeRepository.findAll(pageRequest);

        page.getContent().forEach(x->productSizeDtos.add(new ProductSizeDto(x)));

        return productSizeDtos;
    }

    @Override
    public List<ProductSizeDto> getAll() {
        List<ProductSizeDto> productSizeDtos = new ArrayList<>();

        productSizeRepository.findAll().forEach(x->productSizeDtos.add(new ProductSizeDto(x)));

        return productSizeDtos;
    }

    public int getSize() {
        return productSizeRepository.findAll().size();
    }

    @Override
    public ProductSizeDto getLast() {
        return getAll().get(getAll().size()-1);
    }

    @Override
    public ProductSizeDto add(ProductSizeDto dtoEntity) {
        ProductSize productSize = dtoEntity.buildEntity();

        productSizeRepository.saveAndFlush(productSize);
        productSizeRepository.refresh(productSize);

        return getLast();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        ProductSize productSize = getProductSize(id);

        for(ProductColor productColor : productSize.getProductColors())
            productColor.getProductSizes().remove(productSize);

        productSizeRepository.delete(productSize);
    }

    @Override
    public ProductSizeDto update(Long id, ProductSizeDto dtoEntity) throws NotFoundException {
        delete(id);

        ProductSize productSize = dtoEntity.buildEntity(id);

        productSizeRepository.saveAndFlush(productSize);
        productSizeRepository.refresh(productSize);

        return getLast();
    }
}
