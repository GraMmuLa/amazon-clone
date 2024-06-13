package com.example.amazonclone.services;

import com.example.amazonclone.dto.DiscountTypeDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.DiscountType;
import com.example.amazonclone.repos.DiscountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountTypeService implements JpaService<DiscountTypeDto, DiscountType, Long> {

    private final DiscountTypeRepository discountTypeRepository;

    @Autowired
    public DiscountTypeService(DiscountTypeRepository discountTypeRepository) {
        this.discountTypeRepository = discountTypeRepository;
    }

    private DiscountType getDiscountType(Long id) throws NotFoundException {

        for(DiscountType discountType : discountTypeRepository.findAll())
            if(discountType.getId().equals(id))
                return discountType;

        throw new NotFoundException("Discount type was not found!");
    }

    @Override
    public DiscountTypeDto get(Long id) throws NotFoundException {
        return new DiscountTypeDto(getDiscountType(id));
    }

    @Override
    public List<DiscountTypeDto> getAll(PageRequest pageRequest) {
        List<DiscountTypeDto> discountTypeDtos = new ArrayList<>();
        Page<DiscountType> page = discountTypeRepository.findAll(pageRequest);

        page.getContent().forEach(x->discountTypeDtos.add(new DiscountTypeDto(x)));

        return discountTypeDtos;
    }

    @Override
    public List<DiscountTypeDto> getAll() {
        List<DiscountTypeDto> discountTypeDtos = new ArrayList<>();

        discountTypeRepository.findAll().forEach(x->discountTypeDtos.add(new DiscountTypeDto(x)));

        return discountTypeDtos;
    }

    public int getSize() {
        return discountTypeRepository.findAll().size();
    }

    @Override
    public DiscountTypeDto getLast() {
        return getAll().get(getAll().size()-1);
    }

    public DiscountTypeDto getByType(String discountTypeName) throws NotFoundException {

        for(DiscountType discountType : discountTypeRepository.findAll())
            if(discountType.getType().equals(discountTypeName))
                return new DiscountTypeDto(discountType);

        throw new NotFoundException("Discount type was not found");
    }

    public List<DiscountTypeDto> getOtherDiscountTypes(String exceptDiscountTypeName) throws NotFoundException {
        List<DiscountTypeDto> discountTypeDtos = new ArrayList<>();

        getByType(exceptDiscountTypeName);

        discountTypeRepository.findAll().forEach(x -> {
            if(!x.getType().equals(exceptDiscountTypeName))
                discountTypeDtos.add(new DiscountTypeDto(x));
        });

        return discountTypeDtos;
    }

    @Override
    public DiscountTypeDto add(DiscountTypeDto dtoEntity) {
        DiscountType discountType = dtoEntity.buildEntity();

        discountTypeRepository.saveAndFlush(discountType);
        discountTypeRepository.refresh(discountType);

        return getLast();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        discountTypeRepository.delete(getDiscountType(id));
    }

    @Override
    public DiscountTypeDto update(Long id, DiscountTypeDto dtoEntity) throws NotFoundException {
        delete(id);

        DiscountType discountType = dtoEntity.buildEntity(id);

        discountTypeRepository.saveAndFlush(discountType);
        discountTypeRepository.refresh(discountType);

        return getLast();
    }
}
