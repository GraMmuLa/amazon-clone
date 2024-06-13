package com.example.amazonclone.services;

import com.example.amazonclone.dto.ProductDetailValueDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.Product;
import com.example.amazonclone.models.ProductDetailKey;
import com.example.amazonclone.models.ProductDetailValue;
import com.example.amazonclone.repos.ProductDetailKeyRepository;
import com.example.amazonclone.repos.ProductDetailValueRepository;
import com.example.amazonclone.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDetailValueService implements JpaService<ProductDetailValueDto, ProductDetailValue, Long> {
    private final ProductDetailValueRepository productDetailValueRepository;
    private final ProductDetailKeyRepository productDetailKeyRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductDetailValueService(ProductDetailValueRepository productDetailValueRepository, ProductDetailKeyRepository productDetailKeyRepository, ProductRepository productRepository) {
        this.productDetailValueRepository = productDetailValueRepository;
        this.productDetailKeyRepository = productDetailKeyRepository;
        this.productRepository = productRepository;
    }

    private ProductDetailValue getProductDetailValue(Long id) throws NotFoundException {
        for(ProductDetailValue productDetailValue : productDetailValueRepository.findAll())
            if(productDetailValue.getId().equals(id))
                return productDetailValue;
        throw new NotFoundException("Product detail value was not found!");
    }

    @Override
    public ProductDetailValueDto get(Long id) throws NotFoundException {
        return new ProductDetailValueDto(getProductDetailValue(id));
    }

    @Override
    public List<ProductDetailValueDto> getAll(PageRequest pageRequest) {
        List<ProductDetailValueDto> productDetailValueDtos = new ArrayList<>();
        Page<ProductDetailValue> page = productDetailValueRepository.findAll(pageRequest);

        page.getContent().forEach(x->productDetailValueDtos.add(new ProductDetailValueDto(x)));

        return productDetailValueDtos;
    }

    @Override
    public List<ProductDetailValueDto> getAll() {
        List<ProductDetailValueDto> productDetailValueDtos = new ArrayList<>();

        productDetailValueRepository.findAll().forEach(x->productDetailValueDtos.add(new ProductDetailValueDto(x)));

        return productDetailValueDtos;
    }

    public int getSize() {
        return productDetailValueRepository.findAll().size();
    }

    @Override
    public ProductDetailValueDto getLast() {
        return getAll().get(getAll().size()-1);
    }

    @Override
    public ProductDetailValueDto add(ProductDetailValueDto dtoEntity) throws NotFoundException {
        ProductDetailValue productDetailValue = dtoEntity.buildEntity();

        for (Product product : productRepository.findAll())
            if(product.getId().equals(dtoEntity.getProductId()))
                productDetailValue.setProduct(product);
        if (productDetailValue.getProduct() == null)
            throw new NotFoundException("Product was not found");

        for(ProductDetailKey productDetailKey : productDetailKeyRepository.findAll())
            if(productDetailKey.getId().equals(dtoEntity.getProductDetailKeyId()))
                productDetailValue.setProductDetailKey(productDetailKey);
        if(productDetailValue.getProductDetailKey() == null)
            throw new NotFoundException("Product detail key was not found!");

        productDetailValueRepository.saveAndFlush(productDetailValue);
        productDetailValueRepository.refresh(productDetailValue);

        return getLast();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        productDetailValueRepository.delete(getProductDetailValue(id));
    }

    @Override
    public ProductDetailValueDto update(Long id, ProductDetailValueDto dtoEntity) throws NotFoundException {
        delete(id);

        ProductDetailValue productDetailValue = dtoEntity.buildEntity(id);

        productDetailValueRepository.saveAndFlush(productDetailValue);
        productDetailValueRepository.refresh(productDetailValue);

        return getLast();
    }
}
