package com.example.amazonclone.dto;

import com.example.amazonclone.Image;
import com.example.amazonclone.models.Banner;
import com.example.amazonclone.models.CategoryImage;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;

public class BannerDto extends Image implements DtoEntity<Banner, Long> {
    @Getter
    @Setter
    @Nullable
    private Long id;

    @Getter
    @Setter
    private Long userId;

    @Getter
    @Setter
    private Timestamp createdAt;

    public BannerDto(Banner entity) {
        super(entity.getImage());
        this.id = entity.getId();
        userId = entity.getUser().getId();
        this.createdAt = entity.getCreatedAt();
    }

    public BannerDto(MultipartFile file, Long userId) throws IOException {
        super(file);
        this.userId = userId;
    }

    @Override
    public Banner buildEntity() {

        Banner banner = new Banner();

        if(id != null)
            banner.setId(id);
        banner.setImage(data);

        return banner;
    }

    @Override
    public Banner buildEntity(Long id) {
        this.id = id;
        return buildEntity();
    }

    public BannerDto deflateImage() {
        decompressImage();
        return this;
    }
}
