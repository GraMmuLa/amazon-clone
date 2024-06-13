package com.example.amazonclone.dto;

import com.example.amazonclone.models.ProductSize;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductSizeDto implements DtoEntity<ProductSize, Long> {

    @Nullable
    private Long id;

    private String size;

    private List<Long> productColorIds = new ArrayList<>();

    private Timestamp createdAt;

    public ProductSizeDto(ProductSize entity) {
        this.id = entity.getId();
        this.size = entity.getSize();
        if(entity.getProductColors() != null)
            entity.getProductColors().forEach(x->productColorIds.add(x.getId()));
        this.createdAt = entity.getCreatedAt();
    }

    public ProductSizeDto(String size) {
        this.size = size;
    }

    @Override
    public ProductSize buildEntity() {
        ProductSize productSize = new ProductSize();

        if(id != null)
            productSize.setId(id);
        productSize.setSize(size);

        return productSize;
    }

    @Override
    public ProductSize buildEntity(Long id) {
        ProductSize productSize = buildEntity();
        productSize.setId(id);
        return productSize;
    }
}
