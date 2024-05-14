package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.ProductColorDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.ProductColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/productColor")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductColorController {
    private final ProductColorService productColorService;

    @Autowired
    public ProductColorController(ProductColorService productColorService) {
        this.productColorService = productColorService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductColorDto>> getProductColors(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity,
            @RequestParam(required = false, defaultValue = "") String sortBy,
            @RequestParam(required = false, defaultValue = "0") Double priceFrom,
            @RequestParam(required = false, defaultValue = "0") Double priceTo) {
        if(!sortBy.equals("")) {
            return switch (sortBy) {
                case "price_asc" ->
                        ResponseEntity.ok(productColorService.getAllByPriceAsc(PageRequest.of(page, quantity)));
                case "price_desc" ->
                        ResponseEntity.ok(productColorService.getAllByPriceDesc(PageRequest.of(page, quantity)));
                case "new" ->
                        ResponseEntity.ok(productColorService.getAllByCreatedAtAsc(PageRequest.of(page, quantity)));
                default ->
                        ResponseEntity.ok(productColorService.getAll(PageRequest.of(page, quantity)));
            };
        }
        if(priceTo == 0 && priceFrom == 0)
            return ResponseEntity.ok(productColorService.getAll());
        else if(priceTo == 0 && priceFrom != 0)
            return ResponseEntity.ok(productColorService.getAllByPriceFromTo(priceFrom, Double.MAX_VALUE, PageRequest.of(page, quantity)));
        return ResponseEntity.ok(productColorService.getAllByPriceFromTo(priceFrom, priceTo, PageRequest.of(page, quantity)));
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getProductColorsSize() {
        return ResponseEntity.ok(productColorService.getSize());
    }

    @GetMapping
    public ResponseEntity<ProductColorDto> getProductColor(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(productColorService.get(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductColorDto>> getProductColorsByProductId(@RequestParam Long productId) {
        return ResponseEntity.ok(productColorService.getAllByProductId(productId));
    }

    @PostMapping
    public ResponseEntity<ProductColorDto> add(@RequestBody ProductColorDto productColorDto) {
        try {
            return ResponseEntity.ok(productColorService.add(productColorDto));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/withImage")
    public ResponseEntity<ProductColorDto> addWithImage(@RequestParam("files") MultipartFile[] files,
                                                        @RequestParam Double price,
                                                        @RequestParam Long colorId,
                                                        @RequestParam Long productId) {
        try {
            return ResponseEntity.ok(productColorService.addWithImages(files, new ProductColorDto(price, colorId, productId)));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IOException | EntityAlreadyExistsException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/size")
    public ResponseEntity<String> addProductColorSize(@RequestParam Long productColorId, @RequestParam Long productSizeId) {
        try {
            productColorService.addProductSize(productColorId, productSizeId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/mainImage")
    public ResponseEntity<String> setProductColorMainImage(@RequestParam Long productColorId, @RequestParam Long productColorImageId) {
        try {
            productColorService.setMainImage(productColorId, productColorImageId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductColor(@RequestParam Long id) {
        try {
            productColorService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/size")
    public ResponseEntity<String> deleteProductColorSize(@RequestParam Long productColorId, @RequestParam Long productSizeId) {
        try {
            productColorService.deleteProductColorSize(productColorId, productSizeId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
