package com.example.amazonclone.dto;

import com.example.amazonclone.models.Subcategory;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SubcategoryDto implements DtoEntity<Subcategory, Long> {
    @Nullable
    private Long id;
    private String name;
    private Long categoryId;
    @Nullable
    private Long subcategoryImageId;
    private List<Long> productTypeIds = new ArrayList<>();
    private List<Long> colorIds = new ArrayList<>();
    private Timestamp createdAt;

    public SubcategoryDto(Subcategory entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.categoryId = entity.getCategory().getId();
        if(entity.getSubcategoryImage() != null)
            this.subcategoryImageId = entity.getSubcategoryImage().getId();
        entity.getProductTypes().forEach(x->productTypeIds.add(x.getId()));
        entity.getColors().forEach(x->colorIds.add(x.getId()));
        this.createdAt = entity.getCreatedAt();
    }

    public SubcategoryDto(String name, Long categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    @Override
    public Subcategory buildEntity() {
        Subcategory subcategory = new Subcategory();
        if(id != null)
            subcategory.setId(id);
        subcategory.setName(name);

        return subcategory;
    }

    @Override
    public Subcategory buildEntity(Long id) {
        Subcategory subcategory = buildEntity();
        subcategory.setId(id);
        return subcategory;
    }
}
