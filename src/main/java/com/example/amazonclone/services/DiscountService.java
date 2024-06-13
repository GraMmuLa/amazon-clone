package com.example.amazonclone.services;

import com.example.amazonclone.dto.DiscountDto;
import com.example.amazonclone.dto.DiscountTypeDto;
import com.example.amazonclone.dto.ProductColorDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.Discount;
import com.example.amazonclone.models.DiscountType;
import com.example.amazonclone.models.ProductColor;
import com.example.amazonclone.repos.DiscountRepository;
import com.example.amazonclone.repos.DiscountTypeRepository;
import com.example.amazonclone.repos.ProductColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class DiscountService implements JpaService<DiscountDto, Discount, Long> {
    private final DiscountRepository discountRepository;
    private final ProductColorRepository productColorRepository;
    private final DiscountTypeRepository discountTypeRepository;

    @Autowired
    public DiscountService(DiscountRepository repository,
                           ProductColorRepository productColorRepository,
                           DiscountTypeRepository discountTypeRepository) {
        this.discountRepository = repository;
        this.productColorRepository = productColorRepository;
        this.discountTypeRepository = discountTypeRepository;
    }

    private Discount getDiscount(Long id) throws NotFoundException {
        Iterable<Discount> discounts = discountRepository.findAll();

        for(Discount discount : discounts) {
            if(discount.getId().equals(id))
                return discount;
        }

        throw new NotFoundException("Discount was not found");
    }

    @Override
    public DiscountDto get(Long id) throws NotFoundException {
        return new DiscountDto(getDiscount(id));
    }

    @Override
    public List<DiscountDto> getAll(PageRequest pageRequest) {
        List<DiscountDto> discountDtos = new LinkedList<>();
        Page<Discount> page = discountRepository.findAll(pageRequest);

        page.getContent().forEach(x->discountDtos.add(new DiscountDto(x)));

        return discountDtos;
    }

    @Override
    public List<DiscountDto> getAll() {
        List<DiscountDto> discountDtos = new LinkedList<>();

        discountRepository.findAll().forEach(x->discountDtos.add(new DiscountDto(x)));

        return discountDtos;
    }

    public int getSize() {
        return discountRepository.findAll().size();
    }

    public DiscountDto getByProductColorId(Long productColorId) throws NotFoundException {
        for (ProductColor productColor : productColorRepository.findAll()) {
            if(productColor.getId().equals(productColorId)) {
                if(productColor.getDiscount() != null)
                    return new DiscountDto(productColor.getDiscount());
                throw new NotFoundException("Discount was not found!");
            }
        }
        throw new NotFoundException("Product color was not found!");
    }

    @Override
    public DiscountDto getLast() {
        return getAll().get(getAll().size()-1);
    }

    @Override
    public DiscountDto add(DiscountDto dtoEntity) throws NotFoundException, EntityAlreadyExistsException {
        Discount discount = dtoEntity.buildEntity();

        //TODO
        for (ProductColor productColor : productColorRepository.findAll()) {
            if(productColor.getId().equals(dtoEntity.getProductColorId())) {
                if(productColor.getDiscount() != null)
                    throw new EntityAlreadyExistsException("Discount already exists in productColor!");
                discount.setProductColor(productColor);
            }
        }

        for (DiscountType discountType : discountTypeRepository.findAll()) {
            if(discountType.getId().equals(dtoEntity.getDiscountTypeId()))
                discount.setDiscountType(discountType);
        }

        if(discount.getProductColor() == null)
            throw new NotFoundException("Product color was not found");
        if(discount.getDiscountType() == null)
            throw new NotFoundException("Discount type was not found");

        discountRepository.saveAndFlush(discount);
        discountRepository.refresh(discount);

        return getLast();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Discount discount = getDiscount(id);

        discountRepository.delete(discount);
    }

    @Override
    public DiscountDto update(Long id, DiscountDto dtoEntity) throws NotFoundException {
        delete(id);

        Discount discount = dtoEntity.buildEntity(id);

        discountRepository.saveAndFlush(discount);
        discountRepository.refresh(discount);

        return getLast();
    }
}
