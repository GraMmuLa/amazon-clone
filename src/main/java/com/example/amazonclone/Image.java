package com.example.amazonclone;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
public class Image {
    protected byte[] data;

    public Image(MultipartFile file) throws IOException {
        data = ImageUtil.compressImage(file.getBytes());
    }

    public Image(byte[] data) {
        this.data = data;
        ImageUtil.compressImage(data);
    }

    protected void decompressImage() {
        data = ImageUtil.decompressImage(data);
    }
}
