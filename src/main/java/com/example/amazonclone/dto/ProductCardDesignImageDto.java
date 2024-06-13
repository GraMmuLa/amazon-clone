package com.example.amazonclone.dto;

import com.example.amazonclone.Image;
import com.example.amazonclone.models.ProductCardDesignImage;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;

public class ProductCardDesignImageDto extends Image implements DtoEntity<ProductCardDesignImage, Long> {
    @Nullable
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Long productCardDesignId;

    @Getter
    @Setter
    private Timestamp createdAt;

    public ProductCardDesignImageDto(MultipartFile file, Long productCardDesignId) throws IOException {
        super(file);
        this.productCardDesignId = productCardDesignId;
    }

    public ProductCardDesignImageDto(ProductCardDesignImage entity) {
        super(entity.getImage());
        this.id = entity.getId();
        this.productCardDesignId = entity.getProductCardDesign().getId();
        this.createdAt = entity.getCreatedAt();
    }


    @Override
    public ProductCardDesignImage buildEntity() {
        ProductCardDesignImage productCardDesignImage = new ProductCardDesignImage();

        if(id != null)
            productCardDesignImage.setId(id);
        productCardDesignImage.setImage(data);
        return productCardDesignImage;
    }

    @Override
    public ProductCardDesignImage buildEntity(Long id) {
        this.id = id;

        return buildEntity();
    }

    public ProductCardDesignImageDto deflateImage() {
        decompressImage();
        return this;
    }
}
