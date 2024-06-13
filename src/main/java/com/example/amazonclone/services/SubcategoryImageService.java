package com.example.amazonclone.services;

import com.example.amazonclone.dto.SubcategoryImageDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.*;
import com.example.amazonclone.repos.SubcategoryImageRepository;
import com.example.amazonclone.repos.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SubcategoryImageService implements JpaService<SubcategoryImageDto, SubcategoryImage, Long> {

    private final SubcategoryImageRepository subcategoryImageRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Autowired
    public SubcategoryImageService(SubcategoryImageRepository subcategoryImageRepository, SubcategoryRepository subcategoryRepository) {
        this.subcategoryImageRepository = subcategoryImageRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    private SubcategoryImage getImage(Long id) throws NotFoundException {
        Iterable<SubcategoryImage> subcategoryImages = subcategoryImageRepository.findAll();

        for (SubcategoryImage subcategoryImage : subcategoryImages) {
            if(subcategoryImage.getId().equals(id))
                return subcategoryImage;
        }
        throw new NotFoundException("Subcategory image was not found");
    }

    @Override
    public SubcategoryImageDto get(Long id) throws NotFoundException {
        return new SubcategoryImageDto(getImage(id));
    }

    @Override
    public List<SubcategoryImageDto> getAll(PageRequest pageRequest) {
        List<SubcategoryImageDto> subcategoryImageDtos = new LinkedList<>();
        Page<SubcategoryImage> page = subcategoryImageRepository.findAll(pageRequest);

        page.getContent().forEach(x->subcategoryImageDtos.add(new SubcategoryImageDto(x).deflateImage()));

        return subcategoryImageDtos;
    }

    public SubcategoryImageDto getBySubcategory(Long subcategoryId) throws NotFoundException {
        for(Subcategory subcategory : subcategoryRepository.findAll()) {
            if(subcategory.getId().equals(subcategoryId)) {
                if(subcategory.getSubcategoryImage() != null)
                    return new SubcategoryImageDto(subcategory.getSubcategoryImage());
                throw new NotFoundException("Subcategory image was not found");
            }
        }
        throw new NotFoundException("Subcategory was not found");
    }

    public int getSize() {
        return subcategoryImageRepository.findAll().size();
    }

    @Override
    public List<SubcategoryImageDto> getAll() {
        List<SubcategoryImageDto> subcategoryImageDtos = new LinkedList<>();

        subcategoryImageRepository.findAll().forEach(x->subcategoryImageDtos.add(new SubcategoryImageDto(x).deflateImage()));

        return subcategoryImageDtos;
    }

    @Override
    public SubcategoryImageDto getLast() {
        return getAll().get(getAll().size()-1);
    }

    @Override
    public SubcategoryImageDto add(SubcategoryImageDto dtoEntity) throws NotFoundException, EntityAlreadyExistsException {

        for (SubcategoryImage subcategoryImage : subcategoryImageRepository.findAll()) {
            if(subcategoryImage.getSubcategory().getId().equals(dtoEntity.getSubcategoryId()))
                throw new EntityAlreadyExistsException("Subcategory image already exists");
        }

        SubcategoryImage subcategoryImage = dtoEntity.buildEntity();

        Iterable<Subcategory> subcategories = subcategoryRepository.findAll();

        for (Subcategory subcategory : subcategories) {
            if(subcategory.getId().equals(dtoEntity.getSubcategoryId()))
                subcategoryImage.setSubcategory(subcategory);
        }

        if(subcategoryImage.getSubcategory() == null)
            throw new NotFoundException("Product was not found");

        subcategoryImageRepository.saveAndFlush(subcategoryImage);
        subcategoryImageRepository.refresh(subcategoryImage);

        return getLast();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        SubcategoryImage image = getImage(id);

        subcategoryImageRepository.delete(image);
    }

    @Override
    public SubcategoryImageDto update(Long id, SubcategoryImageDto dtoEntity) throws NotFoundException {
        delete(id);

        SubcategoryImage subcategoryImage = dtoEntity.buildEntity(id);

        subcategoryImageRepository.saveAndFlush(subcategoryImage);
        subcategoryImageRepository.refresh(subcategoryImage);

        return getLast();
    }
}
