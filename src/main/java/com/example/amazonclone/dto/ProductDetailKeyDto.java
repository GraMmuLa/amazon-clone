package com.example.amazonclone.dto;

import com.example.amazonclone.models.ProductDetailKey;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDetailKeyDto implements DtoEntity<ProductDetailKey, Long> {

    @Nullable
    private Long id;

    private String key;

    private Long productTypeId;

    @Nullable
    private List<Long> productDetailValueId = new ArrayList<>();

    private Timestamp createdAt;

    public ProductDetailKeyDto(ProductDetailKey entity) {
        this.id = entity.getId();
        this.key = entity.getKey();
        this.productTypeId = entity.getProductType().getId();
        entity.getProductDetailValues().forEach(x->productDetailValueId.add(x.getId()));
        this.createdAt = entity.getCreatedAt();
    }

    public ProductDetailKeyDto(String key, Long productTypeId) {
        this.key = key;
        this.productTypeId = productTypeId;
    }

    @Override
    public ProductDetailKey buildEntity() {
        ProductDetailKey productDetailKey = new ProductDetailKey();

        if(id != null)
            productDetailKey.setId(id);
        productDetailKey.setKey(key);

        return productDetailKey;
    }

    @Override
    public ProductDetailKey buildEntity(Long id) {
        ProductDetailKey productDetailKey = buildEntity();
        productDetailKey.setId(id);
        return productDetailKey;
    }
}
