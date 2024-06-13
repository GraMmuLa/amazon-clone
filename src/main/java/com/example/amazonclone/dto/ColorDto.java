package com.example.amazonclone.dto;

import com.example.amazonclone.models.Color;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ColorDto implements DtoEntity<Color, Long> {

    @Nullable
    private Long id;

    private String color;
    private Long subcategoryId;

    private List<Long> productColorsIds = new ArrayList<>();

    private Timestamp createdAt;

    public ColorDto(String color) {
        this.color = color;
    }

    public ColorDto(Color entity) {
        this(entity.getColor());
        this.id = entity.getId();
        this.subcategoryId = entity.getSubcategory().getId();
        if(entity.getProductColors() != null)
            entity.getProductColors().forEach(productColor -> productColorsIds.add(productColor.getId()));
        this.createdAt = entity.getCreatedAt();
    }

    @Override
    public Color buildEntity() {
        Color colorEntity = new Color();

        if(id != null)
            colorEntity.setId(id);
        colorEntity.setColor(color);

        return colorEntity;
    }

    @Override
    public Color buildEntity(Long id) {
        this.id = id;

        return buildEntity();
    }
}
