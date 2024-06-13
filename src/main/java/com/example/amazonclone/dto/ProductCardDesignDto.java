package com.example.amazonclone.dto;

import com.example.amazonclone.models.ProductCardDesign;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductCardDesignDto implements DtoEntity<ProductCardDesign, Long> {

    @Nullable
    private Long id;
    private String name;
    private String description;
    private Long productCardDesignImageId;
    private List<Long> productCardIds = new ArrayList<>();
    private Timestamp createdAt;

    public ProductCardDesignDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ProductCardDesignDto(ProductCardDesign entity) {
        this(entity.getName(), entity.getDescription());
        this.id = entity.getId();
        this.createdAt = entity.getCreatedAt();
        entity.getProductCards().forEach(productCard->productCardIds.add(productCard.getId()));
        if(entity.getProductCardDesignImage() != null)
            this.productCardDesignImageId = entity.getProductCardDesignImage().getId();
    }

    @Override
    public ProductCardDesign buildEntity() {
        ProductCardDesign productCardDesign = new ProductCardDesign();

        if(id != null)
            productCardDesign.setId(id);
        productCardDesign.setName(name);
        productCardDesign.setDescription(description);
        if(createdAt != null)
            productCardDesign.setCreatedAt(createdAt);

        return productCardDesign;
    }

    @Override
    public ProductCardDesign buildEntity(Long id) {
        this.id = id;

        return buildEntity();
    }
}
