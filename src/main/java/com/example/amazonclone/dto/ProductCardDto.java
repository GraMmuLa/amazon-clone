package com.example.amazonclone.dto;

import com.example.amazonclone.models.ProductCard;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProductCardDto implements DtoEntity<ProductCard, Long> {
    @Nullable
    private Long id;
    private Double price;
    private String fromWho;
    private String message;
    private String email;
    private String description;
    private Timestamp createdAt;

    public ProductCardDto(Double price,
                          String fromWho,
                          String message,
                          String email,
                          String description) {
        this.price = price;
        this.fromWho = fromWho;
        this.message = message;
        this.email = email;
        this.description = description;
    }

    public ProductCardDto(ProductCard entity) {
        this(entity.getPrice(),
                entity.getFromWho(),
                entity.getMessage(),
                entity.getEmail(),
                entity.getDescription());
        this.id = entity.getId();
        this.createdAt = entity.getCreatedAt();
    }

    @Override
    public ProductCard buildEntity() {

        ProductCard productCard = new ProductCard();

        if(id != null)
            productCard.setId(id);

        productCard.setPrice(price);
        productCard.setFromWho(fromWho);
        productCard.setMessage(message);
        productCard.setEmail(email);
        productCard.setDescription(description);

        return null;
    }

    @Override
    public ProductCard buildEntity(Long id) {
        this.id = id;
        return buildEntity();
    }
}
