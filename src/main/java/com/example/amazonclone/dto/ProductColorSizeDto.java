package com.example.amazonclone.dto;

import com.example.amazonclone.models.ProductColorImage;
import com.example.amazonclone.models.ProductColorSize;
import com.example.amazonclone.models.ProductSize;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProductColorSizeDto implements DtoEntity<ProductColorSize, Long> {

    @Nullable
    private Long id;
    private Long productColorId;
    private Long productSizeId;
    private Timestamp createdAt;

    public ProductColorSizeDto(Long productColorId, Long productSizeId) {
        this.productColorId = productColorId;
        this.productSizeId = productSizeId;
    }

    public ProductColorSizeDto(ProductColorSize entity) {
        this(entity.getProductColorId(), entity.getProductSizeId());
        this.id = entity.getId();
        this.createdAt = entity.getCreatedAt();
    }

    @Override
    public ProductColorSize buildEntity() {
        ProductColorSize productColorSize = new ProductColorSize();

        if(id != null)
            productColorSize.setId(id);

        productColorSize.setProductColorId(productColorId);
        productColorSize.setProductSizeId(productSizeId);

        return productColorSize;
    }

    @Override
    public ProductColorSize buildEntity(Long id) {
        this.id = id;

        return buildEntity();
    }
}
