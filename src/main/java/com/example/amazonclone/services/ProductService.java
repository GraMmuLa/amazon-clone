package com.example.amazonclone.services;

import com.example.amazonclone.dto.DiscountDto;
import com.example.amazonclone.dto.ProductDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.*;
import com.example.amazonclone.repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService implements JpaService<ProductDto, Product, Long> {

    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final DiscountTypeRepository discountTypeRepository;
    private final DiscountRepository discountRepository;
    private final UserRepository userRepository;

    private Product getProduct(Long id) throws NotFoundException {
        Iterable<Product> products = productRepository.findAll();

        for (Product product : products)
            if(product.getId().equals(id))
                return product;
        throw new NotFoundException("Product was not found");
    }

    @Override
    public ProductDto get(Long id) throws NotFoundException {
        return new ProductDto(getProduct(id));
    }

    @Override
    public List<ProductDto> getAll() {
        List<ProductDto> productDtos = new LinkedList<>();

        productRepository.findAll().forEach(x->productDtos.add(new ProductDto(x)));

        return productDtos;
    }

    @Override
    public ProductDto getLast() {
        return getAll().get(getAll().size()-1);
    }

    @Override
    public List<ProductDto> getAll(PageRequest pageRequest) {

        List<ProductDto> productDtos = new ArrayList<>();
        Page<Product> page = productRepository.findAll(pageRequest);

        page.getContent().forEach(x->productDtos.add(new ProductDto(x)));

        return productDtos;
    }

    public int getSize() {
        return productRepository.findAll().size();
    }

    public List<ProductDto> getAllByDiscountTypeName(String discountTypename) throws NotFoundException {

        List<ProductDto> products = new ArrayList<>();

        for (DiscountType discountType : discountTypeRepository.findAll()) {
            if(discountType.getType().equals(discountTypename)) {
                for(Discount discount : discountRepository.findAll()) {
                    if(discount.getDiscountType() == discountType) {
                        if(discount.getProductColor() != null)
                            products.add(new ProductDto(discount.getProductColor().getProduct()));
                    }
                }
                return products;
            }
        }
        throw new NotFoundException("Discount type was not found");
    }

    public List<ProductDto> getAllByUser(Long userId) throws NotFoundException {
        List<ProductDto> productDtos = new ArrayList<>();

        List<Product> products = productRepository.findAllByUserId(userId)
                .orElseThrow(()->new NotFoundException("Products was not found!"));

        products.forEach(product -> productDtos.add(new ProductDto(product)));

        return productDtos;
    }

    @Override
    public ProductDto add(ProductDto dtoEntity) throws NotFoundException {
        Product product = dtoEntity.buildEntity();

        Iterable<ProductType> productTypes = productTypeRepository.findAll();
        for (ProductType productType : productTypes) {
            if(productType.getId().equals(dtoEntity.getProductTypeId()))
                product.setProductType(productType);
        }
        if(product.getProductType() == null)
            throw new NotFoundException("Subcategory was not found");

        User user = userRepository.findById(dtoEntity.getUserId())
                .orElseThrow(()->new NotFoundException("User was not found!"));

        product.setUser(user);

        productRepository.saveAndFlush(product);
        productRepository.refresh(product);

        return getLast();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Product product = getProduct(id);

        productRepository.delete(product);
    }

    @Override
    public ProductDto update(Long id, ProductDto dtoEntity) throws NotFoundException {
        delete(id);

        Product product = dtoEntity.buildEntity(id);

        productRepository.saveAndFlush(product);
        productRepository.refresh(product);

        return getLast();
    }
}
