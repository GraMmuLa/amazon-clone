package com.example.amazonclone.services;

import com.example.amazonclone.dto.ProductColorDto;
import com.example.amazonclone.dto.ProductColorImageDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.*;
import com.example.amazonclone.repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductColorService implements JpaService<ProductColorDto, ProductColor, Long> {

    private final ProductColorRepository productColorRepository;
    private final ProductRepository productRepository;
    private final ProductSizeRepository productSizeRepository;
    private final ColorRepository colorRepository;
    private final ProductColorImageService productColorImageService;
    private final ProductColorSizeRepository productColorSizeRepository;
    private final ProductTypeRepository productTypeRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final UserRepository userRepository;

    private ProductColor getProductColor(Long id) throws NotFoundException {
        for(ProductColor productColor : productColorRepository.findAll())
            if(productColor.getId().equals(id))
                return productColor;
        throw new NotFoundException("Product color was not found!");
    }

    @Override
    public ProductColorDto get(Long id) throws NotFoundException {
        return new ProductColorDto(getProductColor(id));
    }

    @Override
    public List<ProductColorDto> getAll(PageRequest pageRequest) {
        List<ProductColorDto> productColorDtos = new ArrayList<>();
        Page<ProductColor> page = productColorRepository.findAll(pageRequest);

        page.getContent().forEach(x->productColorDtos.add(new ProductColorDto(x)));

        return productColorDtos;
    }

    @Override
    public List<ProductColorDto> getAll() {
        List<ProductColorDto> productColorDtos = new ArrayList<>();

        productColorRepository.findAll().forEach(x->productColorDtos.add(new ProductColorDto(x)));

        return productColorDtos;
    }

    public int getSize() {
        return productColorRepository.findAll().size();
    }

    public List<ProductColorDto> getAllByPriceAsc() {
        List<ProductColorDto> productColorDtos = new ArrayList<>();

        productColorRepository.findAllPriceAsc().forEach(x->productColorDtos.add(new ProductColorDto(x)));

        return productColorDtos;
    }

    public List<ProductColorDto> getAllByPriceAsc(PageRequest pageRequest) {
        List<ProductColorDto> productColorDtos = new ArrayList<>();

        Page<ProductColor> page = productColorRepository.findAllPriceAsc(pageRequest);
        page.getContent().forEach(x->{
            if(x.getDiscount() != null)
                x.setPrice(x.getPrice()+x.getDiscount().getPrice());
        });
        page.getContent().forEach(x->productColorDtos.add(new ProductColorDto(x)));

        return productColorDtos;
    }

    public List<ProductColorDto> getAllByPriceDesc() {
        List<ProductColorDto> productColorDtos = new ArrayList<>();

        productColorRepository.findAllPriceDesc().forEach(x->productColorDtos.add(new ProductColorDto(x)));

        return productColorDtos;
    }

    public List<ProductColorDto> getAllByPriceDesc(PageRequest pageRequest) {
        List<ProductColorDto> productColorDtos = new ArrayList<>();

        Page<ProductColor> page = productColorRepository.findAllPriceDesc(pageRequest);
        page.getContent().forEach(x->{
            if(x.getDiscount() != null)
                x.setPrice(x.getPrice()+x.getDiscount().getPrice());
        });
        page.getContent().forEach(x->productColorDtos.add(new ProductColorDto(x)));

        return productColorDtos;
    }


    public List<ProductColorDto> getAllByCreatedAtAsc() {
        List<ProductColorDto> productColorDtos = new ArrayList<>();

        productColorRepository.findAllByOrderByCreatedAtDesc().forEach(x->productColorDtos.add(new ProductColorDto(x)));

        return productColorDtos;
    }


    public List<ProductColorDto> getAllByCreatedAtAsc(PageRequest pageRequest) {
        List<ProductColorDto> productColorDtos = new ArrayList<>();

        Page<ProductColor> page = productColorRepository.findAllByOrderByCreatedAtDesc(pageRequest);
        page.getContent().forEach(x->productColorDtos.add(new ProductColorDto(x)));

        return productColorDtos;
    }

    public List<ProductColorDto> getAllByPriceFromTo(Double priceFrom, Double priceTo, PageRequest pageRequest) {
        List<ProductColorDto> productColorDtos = new ArrayList<>();

        Page<ProductColor> page = productColorRepository.findAllPriceFromTo(priceFrom, priceTo, pageRequest);
        page.getContent().forEach(x->{
            if(x.getDiscount() != null)
                x.setPrice(x.getPrice()+x.getDiscount().getPrice());
        });
        page.getContent().forEach(x->productColorDtos.add(new ProductColorDto(x)));

        return productColorDtos;
    }


    public List<ProductColorDto> getAllByProductId(Long productId) {
        List<ProductColorDto> productColorDtos = new ArrayList<>();

        productColorRepository.findAll().forEach(productColor -> {
            if(productColor.getProduct().getId().equals(productId))
                productColorDtos.add(new ProductColorDto(productColor));
        });

        return productColorDtos;
    }

    public List<ProductColorDto> getAllByProductTypeIds(List<Long> productTypeIds) throws NotFoundException {
        List<ProductColorDto> productColorDtos = new ArrayList<>();

        for(Long productTypeId : productTypeIds) {
            if(!productTypeRepository.existsById(productTypeId))
                throw new NotFoundException("Product type was not found!");
        }

        for (ProductColor productColor : productColorRepository.findAll()) {
            if(productTypeIds.contains(productColor.getProduct().getProductType().getId()))
                productColorDtos.add(new ProductColorDto(productColor));
        }

        return productColorDtos;
    }

    public List<ProductColorDto> getAllBySubcategoryId(Long subcategoryId) throws NotFoundException {
        List<ProductColorDto> productColorDtos = new ArrayList<>();

        Subcategory subcategory = subcategoryRepository.findById(subcategoryId).orElseThrow(()->
                new NotFoundException("Subcategory was not found!"));

        List<Long> productTypeIds = new ArrayList<>();

        subcategory.getProductTypes().forEach(productType -> productTypeIds.add(productType.getId()));

        return getAllByProductTypeIds(productTypeIds);
    }

    public List<ProductColorDto> getAllBySubcategoryName(String subcategoryName) throws NotFoundException {
        List<ProductColorDto> productColorDtos = new ArrayList<>();

        Subcategory subcategory = subcategoryRepository.findByName(subcategoryName).orElseThrow(()->
                new NotFoundException("Subcategory was not found!"));

        List<Long> productTypeIds = new ArrayList<>();

        subcategory.getProductTypes().forEach(productType -> productTypeIds.add(productType.getId()));

        return getAllByProductTypeIds(productTypeIds);
    }

    public List<ProductColorDto> getAllByDiscountFromTo(Long discountPercentFrom, Long discountPercentTo) {
        List<ProductColorDto> productColorDtos = new ArrayList<>();

        for (ProductColor productColor : productColorRepository.findAll()) {
            if(productColor.getDiscount() != null) {
                long discountPercent = Math.round(productColor.getDiscount().getPrice() / productColor.getPrice() / 100);
                if(discountPercent > discountPercentFrom && discountPercent < discountPercentTo)
                    productColorDtos.add(new ProductColorDto(productColor));
            }
        }

        return productColorDtos;
    }

    public List<ProductColorDto> getAllByDiscountFromToFromExisted(List<ProductColorDto> productColorDtos, Long discountPercentFrom) throws NotFoundException {
        List<ProductColorDto> result = new ArrayList<>();

        for (ProductColorDto productColorDto : productColorDtos) {
            ProductColor productColor = productColorRepository.findById(productColorDto.getId())
                    .orElseThrow(()->new NotFoundException("ProductColor was not found"));
            if(productColor.getDiscount() != null) {
                long discountPercent = Math.round(productColor.getDiscount().getPrice() / (productColor.getPrice() / 100D));
                if(discountPercent > discountPercentFrom)
                    result.add(new ProductColorDto(productColor));
            }
        }

        return result;
    }

    public List<ProductColorDto> getAllFavouritedByUser(Long userId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User was not found!"));

        List<ProductColorDto> productColorDtos = new ArrayList<>();

        user.getFavouriteProductColors().forEach(productColor ->
                productColorDtos.add(new ProductColorDto(productColor)));

        return productColorDtos;
    }

    @Override
    public ProductColorDto getLast() {
        return getAll().get(getAll().size()-1);
    }

    @Override
    public ProductColorDto add(ProductColorDto dtoEntity) throws NotFoundException {
        ProductColor productColor = dtoEntity.buildEntity();

        for (Product product : productRepository.findAll())
            if(product.getId().equals(dtoEntity.getProductId()))
                productColor.setProduct(product);
        if(productColor.getProduct() == null)
            throw new NotFoundException("Product was not found!");

        for (Color color : colorRepository.findAll())
            if(color.getId().equals(dtoEntity.getColorId()))
                productColor.setColor(color);
        if(productColor.getColor() == null)
            throw new NotFoundException("Color was not found!");

        productColorRepository.saveAndFlush(productColor);
        productColorRepository.refresh(productColor);

        return getLast();
    }

    public ProductColorDto addWithImages(MultipartFile[] files, ProductColorDto productColorDto) throws IOException, NotFoundException, EntityAlreadyExistsException {
        ProductColorDto responseDto = add(productColorDto);
        for (int i = 0; i < files.length; ++i) {
            ProductColorImageDto productColorImageDto = productColorImageService.add(new ProductColorImageDto(files[i], responseDto.getId()));
            if(i == 0)
                setMainImage(responseDto.getId(), productColorImageDto.getId());
        }
        return responseDto;
    }

    public void addProductSize(Long productColorId, Long productSizeId) throws NotFoundException {
        ProductColor productColor = getProductColor(productColorId);
        for(ProductSize productSize : productSizeRepository.findAll()) {
            if(productSize.getId().equals(productSizeId)) {
                productColor.getProductSizes().add(productSize);
                productSize.getProductColors().add(productColor);
                productSizeRepository.saveAndFlush(productSize);
                productSizeRepository.refresh(productSize);
            }
        }

        productColorRepository.saveAndFlush(productColor);
        productColorRepository.refresh(productColor);
    }


    public void setMainImage(Long productColorId, Long productColorImageId) throws NotFoundException {
        ProductColor productColor = getProductColor(productColorId);

        for (ProductColorImage productColorImage : productColor.getProductColorImages()) {
            if(productColorImage.getId().equals(productColorImageId)) {
                productColor.setMainImage(productColorImage);
                productColorRepository.saveAndFlush(productColor);
                productColorRepository.refresh(productColor);
                return;
            }
        }

        if(productColor.getMainImage() == null)
            throw new NotFoundException("Product color image was not found!");
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        productColorRepository.delete(getProductColor(id));
    }

    public void deleteProductColorSize(Long productColorId, Long productSizeId) throws NotFoundException {
        for(ProductColorSize productColorSize : productColorSizeRepository.findAll())
            if(productColorSize.getProductColorId().equals(productColorId) && productColorSize.getProductSizeId().equals(productSizeId))
                productColorSizeRepository.delete(productColorSize);

        throw new NotFoundException("Product color size was not found!");
    }

    @Override
    public ProductColorDto update(Long id, ProductColorDto dtoEntity) throws NotFoundException {
        delete(id);

        add(dtoEntity);

        return getLast();
    }
}
