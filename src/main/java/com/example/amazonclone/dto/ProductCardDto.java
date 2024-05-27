package com.example.amazonclone.dto;

import com.example.amazonclone.models.ProductCard;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class ProductCardDto implements DtoEntity<ProductCard, Long> {
    @Nullable
    private Long id;
    private Double price;
    private String fromWho;
    @Nullable
    private String message;
    private String email;
    private Long productCardDesignId;
    private Timestamp createdAt;
    @Nullable
    private String code;

    public ProductCardDto(Double price,
                          String fromWho,
                          String message,
                          String email,
                          Long productCardDesignId) {
        this.price = price;
        this.fromWho = fromWho;
        this.message = message;
        this.email = email;
        this.productCardDesignId = productCardDesignId;
    }

    public ProductCardDto(ProductCard entity) {
        this(entity.getPrice(),
                entity.getFromWho(),
                entity.getMessage(),
                entity.getEmail(),
                entity.getProductCardDesign().getId());
        this.id = entity.getId();
        this.code = entity.getCode();
        this.createdAt = entity.getCreatedAt();
    }

    public ProductCardDto(String code,
                          Double price,
                          String fromWho,
                          String message,
                          String email,
                          Long productCardDesignId) {
        this(price, fromWho, message, email, productCardDesignId);
        this.code = code;
    }

    @Override
    public ProductCard buildEntity() {

        ProductCard productCard = new ProductCard();

        if(id != null)
            productCard.setId(id);
        if(code != null)
            productCard.setCode(code);

        productCard.setPrice(price);
        productCard.setFromWho(fromWho);
        productCard.setMessage(message);
        productCard.setEmail(email);

        return productCard;
    }

    @Override
    public ProductCard buildEntity(Long id) {
        this.id = id;
        return buildEntity();
    }
}
