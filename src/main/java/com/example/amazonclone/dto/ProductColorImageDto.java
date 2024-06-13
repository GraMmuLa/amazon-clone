package com.example.amazonclone.dto;

import com.example.amazonclone.Image;
import com.example.amazonclone.models.ProductColorImage;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
public class ProductColorImageDto extends Image implements DtoEntity<ProductColorImage, Long> {
    @Nullable
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Long productColorId;

    @Getter
    @Setter
    private Timestamp createdAt;

    public ProductColorImageDto(ProductColorImage entity) {
        super(entity.getImage());
        this.id = entity.getId();
        this.productColorId = entity.getProductColor().getId();
        this.createdAt = entity.getCreatedAt();
    }

    public ProductColorImageDto(MultipartFile file, Long productColorId) throws IOException {
        super(file);
        this.productColorId = productColorId;
    }

    @Override
    public ProductColorImage buildEntity() {

        ProductColorImage productColorImage = new ProductColorImage();
        if(id != null)
            productColorImage.setId(id);
        productColorImage.setImage(data);

        return productColorImage;
    }

    @Override
    public ProductColorImage buildEntity(Long id) {
        this.id = id;
        return buildEntity();
    }

    public ProductColorImageDto deflateImage() {
        decompressImage();
        return this;
    }
}
