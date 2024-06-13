package com.example.amazonclone.dto;


import com.example.amazonclone.models.ProductType;
import com.example.amazonclone.services.ProductTypeService;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductTypeDto implements DtoEntity<ProductType, Long> {

    @Nullable
    private Long id;

    private String name;

    private Long subcategoryId;

    private List<Long> productIds = new ArrayList<>();

    private List<Long> productDetailKeys = new ArrayList<>();

    private Timestamp createdAt;

    public ProductTypeDto(ProductType entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.subcategoryId = entity.getSubcategory().getId();
        entity.getProducts().forEach(x->productIds.add(x.getId()));
        entity.getProductDetailKeys().forEach(x->productDetailKeys.add(x.getId()));
        this.createdAt = entity.getCreatedAt();
    }

    public ProductTypeDto(String name, Long subcategoryId) {
        this.name = name;
        this.subcategoryId = subcategoryId;
    }

    @Override
    public ProductType buildEntity() {
        ProductType productType = new ProductType();
        if(id != null)
            productType.setId(id);
        productType.setName(name);

        return productType;
    }

    @Override
    public ProductType buildEntity(Long id) {
        ProductType productType = buildEntity();
        productType.setId(id);
        return productType;
    }
}
