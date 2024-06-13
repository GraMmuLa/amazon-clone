package com.example.amazonclone.services;

import com.example.amazonclone.dto.ProductCardDesignDto;
import com.example.amazonclone.dto.ProductCardDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.ProductCard;
import com.example.amazonclone.models.ProductCardDesign;
import com.example.amazonclone.repos.ProductCardDesignRepository;
import com.example.amazonclone.repos.ProductCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class ProductCardService implements JpaService<ProductCardDto, ProductCard, Long> {
    private final ProductCardRepository productCardRepository;
    private final ProductCardDesignRepository productCardDesignRepository;
    private final PasswordEncoder passwordEncoder;

    private String randomCode() {
        String saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * saltChars.length());
            salt.append(saltChars.charAt(index));
        }
        return salt.toString();
    }

    @Override
    public ProductCardDto get(Long id) throws NotFoundException {
        return new ProductCardDto(productCardRepository.findById(id).orElseThrow(()->new NotFoundException("Product card was not found!")));
    }

    @Override
    public List<ProductCardDto> getAll(PageRequest pageRequest) {
        List<ProductCardDto> productCardDtos = new ArrayList<>();

        Page<ProductCard> page = productCardRepository.findAll(pageRequest);
        page.getContent().forEach(productCard -> productCardDtos.add(new ProductCardDto(productCard)));

        return productCardDtos;
    }

    @Override
    public List<ProductCardDto> getAll() {
        List<ProductCardDto> productCardDtos = new ArrayList<>();

        productCardRepository.findAll().forEach(productCard -> productCardDtos.add(new ProductCardDto(productCard)));

        return productCardDtos;
    }

    @Override
    public ProductCardDto getLast() {
        List<ProductCardDto> productCardDtos = getAll();
        return productCardDtos.get(productCardDtos.size()-1);
    }

    @Override
    public ProductCardDto add(ProductCardDto dtoEntity) throws NotFoundException {

        String code = passwordEncoder.encode(randomCode());
        dtoEntity.setCode(code);

        ProductCard productCard = dtoEntity.buildEntity();

        for(ProductCardDesign productCardDesign : productCardDesignRepository.findAll()) {
            if(productCardDesign.getId().equals(dtoEntity.getProductCardDesignId())) {
                productCard.setProductCardDesign(productCardDesign);
                break;
            }
        }

        if(productCard.getProductCardDesign() == null)
            throw new NotFoundException("Product card design was not found!");

        productCardRepository.saveAndFlush(productCard);
        productCardRepository.refresh(productCard);

        return getLast();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if(!productCardRepository.existsById(id))
            throw new NotFoundException("Product card already exists");
        productCardRepository.deleteById(id);
    }

    @Override
    public ProductCardDto update(Long id, ProductCardDto dtoEntity) throws NotFoundException {
        delete(id);

        ProductCard productCard = dtoEntity.buildEntity(id);
        productCardRepository.saveAndFlush(productCard);
        productCardRepository.refresh(productCard);

        return getLast();
    }
}
