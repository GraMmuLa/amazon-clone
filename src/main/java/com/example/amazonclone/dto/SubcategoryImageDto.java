package com.example.amazonclone.dto;

import com.example.amazonclone.Image;
import com.example.amazonclone.models.Subcategory;
import com.example.amazonclone.models.SubcategoryImage;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;

public class SubcategoryImageDto extends Image implements DtoEntity<SubcategoryImage, Long> {
    @Nullable
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private Long subcategoryId;
    @Getter
    @Setter
    private Timestamp createdAt;

    public SubcategoryImageDto(SubcategoryImage entity) {
        super(entity.getImage());
        this.id = entity.getId();
        this.subcategoryId = entity.getSubcategory().getId();
        this.createdAt = entity.getCreatedAt();
    }

    public SubcategoryImageDto(MultipartFile file, Long subcategoryId) throws IOException {
        super(file);
        this.subcategoryId = subcategoryId;
    }

    @Override
    public SubcategoryImage buildEntity() {

        SubcategoryImage subcategoryImage = new SubcategoryImage();
        if(id != null)
            subcategoryImage.setId(id);
        subcategoryImage.setImage(data);

        return subcategoryImage;
    }

    @Override
    public SubcategoryImage buildEntity(Long id) {
        SubcategoryImage subcategoryImage = buildEntity();
        subcategoryImage.setId(id);
        return subcategoryImage;
    }

    public SubcategoryImageDto deflateImage() {
        decompressImage();
        return this;
    }
}
