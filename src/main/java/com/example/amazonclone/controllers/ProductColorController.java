package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.ProductColorDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.Product;
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
    public ResponseEntity<List<ProductColorDto>> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity,
            @RequestParam(required = false, defaultValue = "") String sortBy,
            @RequestParam(required = false, defaultValue = "0") Double priceFrom,
            @RequestParam(required = false, defaultValue = "0") Double priceTo,
            @RequestParam(required = false, defaultValue = "0") Long discountPercentFrom,
            @RequestParam(value = "productTypeId", required = false, defaultValue = "") List<Long> productTypeIds) {

        List<ProductColorDto> productColorDto = productColorService.getAll(PageRequest.of(page, quantity));

        try {
            if(productTypeIds.size() != 0)
                productColorDto = productColorService.getAllByProductTypeIds(productTypeIds);
        } catch (NotFoundException exception) {
            return ResponseEntity.notFound().build();
        }

        //TODO
        switch (sortBy) {
            case "price_asc" -> {
                    List<ProductColorDto> temp = productColorService.getAllByPriceAsc(PageRequest.of(page, quantity));
                    List<ProductColorDto> temp2 = productColorDto;
                    temp.removeIf(x->!temp2.contains(x));
                    productColorDto = temp;
            }
            case "price_desc" -> {
                List<ProductColorDto> temp = productColorService.getAllByPriceDesc(PageRequest.of(page, quantity));
                List<ProductColorDto> temp2 = productColorDto;
                temp.removeIf(x->!temp2.contains(x));
                productColorDto = temp;
            }
            case "new" -> {
                List<ProductColorDto> temp = productColorService.getAllByCreatedAtAsc(PageRequest.of(page, quantity));
                List<ProductColorDto> temp2 = productColorDto;
                temp.removeIf(x->!temp2.contains(x));
                productColorDto = temp;
            }
        }

        if(priceTo == 0D && priceFrom != 0D)
            productColorDto.removeIf(item -> item.getPrice() < priceFrom);
        else if(priceFrom != 0D && priceTo != 0D)
            productColorDto.removeIf(item -> item.getPrice() < priceFrom || item.getPrice() > priceTo);

        if(discountPercentFrom != 0) {
            try {
                productColorDto = productColorService.getAllByDiscountFromToFromExisted(productColorDto, discountPercentFrom);
            } catch (NotFoundException ex) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok(productColorDto);
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getSize() {
        return ResponseEntity.ok(productColorService.getSize());
    }

    @GetMapping
    public ResponseEntity<ProductColorDto> get(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(productColorService.get(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductColorDto>> getAllByProductId(@RequestParam Long productId) {
        return ResponseEntity.ok(productColorService.getAllByProductId(productId));
    }

    @GetMapping("/subcategory")
    public ResponseEntity<List<ProductColorDto>> getAllBySubcategoryId(@RequestParam Long subcategoryId) {
        try {
            return ResponseEntity.ok(productColorService.getAllBySubcategoryId(subcategoryId));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/subcategoryName")
    public ResponseEntity<List<ProductColorDto>> getAllBySubcategoryName(@RequestParam String subcategoryName) {
        try {
            return ResponseEntity.ok(productColorService.getAllBySubcategoryName(subcategoryName));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<ProductColorDto>> getAllFavouritedByUser(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(productColorService.getAllFavouritedByUser(userId));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
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
