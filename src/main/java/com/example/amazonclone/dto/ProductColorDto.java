package com.example.amazonclone.dto;

import com.example.amazonclone.models.ProductColor;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductColorDto implements DtoEntity<ProductColor, Long> {

    @Nullable
    private Long id;

    private Double price;

    private Long colorId;

    private Long productId;

    @Nullable
    private Long discountId;


    private List<Long> productColorImageIds = new ArrayList<>();

    private List<Long> productSizeIds = new ArrayList<>();

    private List<Long> favouritedUserIds = new ArrayList<>();

    private Long mainImageId;

    private Timestamp createdAt;

    public ProductColorDto(ProductColor entity) {
        this.id = entity.getId();
        this.price = entity.getPrice();
        this.colorId = entity.getColor().getId();
        this.productId = entity.getProduct().getId();
        if(entity.getDiscount() != null)
            this.discountId = entity.getDiscount().getId();

        entity.getProductColorImages().forEach(x->productColorImageIds.add(x.getId()));

        entity.getProductSizes().forEach(x->productSizeIds.add(x.getId()));

        if(entity.getFavouritedUsers() != null)
            entity.getFavouritedUsers().forEach(x->favouritedUserIds.add(x.getId()));

        if(entity.getMainImage() != null)
            this.mainImageId = entity.getMainImage().getId();

        this.createdAt = entity.getCreatedAt();
    }

    public ProductColorDto(Double price, Long colorId, Long productId) {
        this.price = price;
        this.colorId = colorId;
        this.productId = productId;
    }

    public ProductColorDto(Double price, Long colorId, Long productId, @Nonnull Long mainImageId) {
        this(price, colorId, productId);
        this.mainImageId = mainImageId;
    }

    @Override
    public ProductColor buildEntity() {
        ProductColor productColor = new ProductColor();

        if(id != null)
            productColor.setId(id);
        productColor.setPrice(price);

        return productColor;
    }

    @Override
    public ProductColor buildEntity(Long id) {
        this.id = id;
        return buildEntity();
    }
}
