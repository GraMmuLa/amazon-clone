package com.example.amazonclone.dto;

import com.example.amazonclone.models.Discount;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class DiscountDto implements DtoEntity<Discount, Long> {
    @Nullable
    private Long id;
    private double price;
    private Timestamp period;
    private Long discountTypeId;
    private Long productColorId;
    private Timestamp createdAt;


    public DiscountDto(Double price, Timestamp period, Long discountTypeId, Long productColorId) {
        this.price = price;
        this.discountTypeId = discountTypeId;
        this.period = period;
        this.productColorId = productColorId;
    }

    public DiscountDto(Discount entity) {
        this.id = entity.getId();
        this.period = entity.getPeriod();
        this.price = entity.getPrice();
        this.discountTypeId = entity.getDiscountType().getId();
        this.productColorId = entity.getProductColor().getId();
        this.createdAt = entity.getCreatedAt();
    }

    @Override
    public Discount buildEntity() {

        Discount discount = new Discount();
        if(id != null)
            discount.setId(id);
        discount.setPrice(price);
        discount.setPeriod(period);

        return discount;
    }

    @Override
    public Discount buildEntity(Long id) {
        Discount discount = buildEntity();
        discount.setId(id);
        return discount;
    }
}
