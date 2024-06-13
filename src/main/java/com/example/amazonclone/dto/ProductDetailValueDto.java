package com.example.amazonclone.dto;

import com.example.amazonclone.models.ProductDetailValue;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class ProductDetailValueDto implements DtoEntity<ProductDetailValue, Long> {

    @Nullable
    private Long id;

    private String value;

    private Long productDetailKeyId;

    private Long productId;

    private Timestamp createdAt;

    public ProductDetailValueDto(ProductDetailValue entity) {
        this.id = entity.getId();
        this.value = entity.getValue();
        this.productDetailKeyId = entity.getProductDetailKey().getId();
        this.productId = entity.getProduct().getId();
        this.createdAt = entity.getCreatedAt();
    }

    public ProductDetailValueDto(Long productId, Long productDetailKeyId, String value) {
        this.value = value;
        this.productDetailKeyId = productDetailKeyId;
        this.productId = productId;
    }

    @Override
    public ProductDetailValue buildEntity() {
        ProductDetailValue productDetailValue = new ProductDetailValue();

        if (id != null)
            productDetailValue.setId(id);
        productDetailValue.setValue(value);

        return productDetailValue;
    }

    @Override
    public ProductDetailValue buildEntity(Long id) {
        ProductDetailValue productDetailValue = buildEntity();
        productDetailValue.setId(id);

        return productDetailValue;
    }
}
