package com.example.amazonclone.services;

import com.example.amazonclone.dto.ProductTypeDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.Product;
import com.example.amazonclone.models.ProductType;
import com.example.amazonclone.models.Subcategory;
import com.example.amazonclone.repos.ProductTypeRepository;
import com.example.amazonclone.repos.SubcategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductTypeService implements JpaService<ProductTypeDto, ProductType, Long> {

    private final ProductTypeRepository productTypeRepository;
    private final SubcategoryRepository subcategoryRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository, SubcategoryRepository subcategoryRepository) {
        this.productTypeRepository = productTypeRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    private ProductType getProductType(Long id) throws NotFoundException {
        for(ProductType productType : productTypeRepository.findAll())
            if(productType.getId().equals(id))
                return productType;
        throw new NotFoundException("Product type was not found!");
    }

    @Override
    public ProductTypeDto get(Long id) throws NotFoundException {
        return new ProductTypeDto(getProductType(id));
    }

    @Override
    public List<ProductTypeDto> getAll(PageRequest pageRequest) {
        List<ProductTypeDto> productTypeDtos = new ArrayList<>();
        Page<ProductType> page = productTypeRepository.findAll(pageRequest);

        page.getContent().forEach(x->productTypeDtos.add(new ProductTypeDto(x)));

        return productTypeDtos;
    }

    @Override
    public List<ProductTypeDto> getAll() {
        List<ProductTypeDto> productTypeDtos = new ArrayList<>();

        productTypeRepository.findAll().forEach(x->productTypeDtos.add(new ProductTypeDto(x)));

        return productTypeDtos;
    }

    public List<ProductTypeDto> getAllBySubcategory(Long subcategoryId) {
        List<ProductTypeDto> productTypeDtos = new ArrayList<>();

        productTypeRepository.findProductTypesBySubcategoryId(subcategoryId)
                .forEach(x->productTypeDtos.add(new ProductTypeDto(x)));

        return productTypeDtos;
    }

    public int getSize() {
        return productTypeRepository.findAll().size();
    };

    @Override
    public ProductTypeDto getLast() {
        return getAll().get(getAll().size()-1);
    }

    @Override
    public ProductTypeDto add(ProductTypeDto dtoEntity) throws NotFoundException {
        ProductType productType = dtoEntity.buildEntity();

        for(Subcategory subcategory : subcategoryRepository.findAll())
            if(subcategory.getId().equals(dtoEntity.getSubcategoryId()))
                productType.setSubcategory(subcategory);
        if(productType.getSubcategory() == null)
            throw new NotFoundException("Subcategory was not found!");

        productTypeRepository.saveAndFlush(productType);
        productTypeRepository.refresh(productType);

        return getLast();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        ProductType productType = getProductType(id);
        productTypeRepository.delete(productType);
    }

    @Override
    public ProductTypeDto update(Long id, ProductTypeDto dtoEntity) throws NotFoundException {
        delete(id);

        ProductType productType = dtoEntity.buildEntity(id);

        productTypeRepository.saveAndFlush(productType);
        productTypeRepository.refresh(productType);

        return getLast();
    }
}
