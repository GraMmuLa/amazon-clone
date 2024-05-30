package com.example.amazonclone.dto;

import com.example.amazonclone.models.Product;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDto implements DtoEntity<Product, Long> {
    @Nullable
    private Long id;
    private String name;
    private String description;
    private Long productTypeId;
    private Long userId;
    private List<Long> productColorsIds = new ArrayList<>();
    private List<Long> productReviewsIds = new ArrayList<>();
    private List<Long> productDetailValuesIds = new ArrayList<>();
    private Timestamp createdAt;

    public ProductDto(String name, Long subcategoryId, Long userId) {
        this.name = name;
        this.userId = userId;
        this.productTypeId = subcategoryId;
    }

    public ProductDto(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.productTypeId = entity.getProductType().getId();
        this.userId = entity.getUser().getId();
        if(entity.getProductColors() != null)
            entity.getProductColors().forEach(x->productColorsIds.add(x.getId()));
        if(entity.getReviews() != null)
            entity.getReviews().forEach(x->this.productReviewsIds.add(x.getId()));
        if(entity.getProductDetailValues() != null)
            entity.getProductDetailValues().forEach(x->this.productDetailValuesIds.add(x.getId()));
        this.createdAt = entity.getCreatedAt();
    }

    @Override
    public Product buildEntity() {

        Product product = new Product();
        if(id != null)
            product.setId(id);
        product.setName(name);
        product.setDescription(description);

        return product;
    }

    @Override
    public Product buildEntity(Long id) {
        Product product = buildEntity();
        product.setId(id);
        return product;
    }
}
