package com.example.amazonclone.services;

import com.example.amazonclone.dto.CategoryImageDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.Category;
import com.example.amazonclone.models.CategoryImage;
import com.example.amazonclone.repos.CategoryImageRepository;
import com.example.amazonclone.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


@Service
public class CategoryImageService implements JpaService<CategoryImageDto, CategoryImage, Long> {

    private final CategoryImageRepository categoryImageRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryImageService(CategoryImageRepository categoryImageRepository, CategoryRepository categoryRepository) {
        this.categoryImageRepository = categoryImageRepository;
        this.categoryRepository = categoryRepository;
    }

    private CategoryImage getImage(Long id) throws NotFoundException {
        Iterable<CategoryImage> categoryImages = categoryImageRepository.findAll();

        for(CategoryImage categoryImage : categoryImages)
            if(id.equals(categoryImage.getId()))
                return categoryImage;
        throw new NotFoundException("CategoryImage was not found");
    }

    public CategoryImageDto getByCategory(Long categoryId) throws NotFoundException {
        for (Category category : categoryRepository.findAll()) {
            if(category.getId().equals(categoryId)) {
                if(category.getImage() != null) {
                    CategoryImageDto categoryImageDto = new CategoryImageDto(category.getImage());
                    return categoryImageDto;
                }
                throw new NotFoundException("Category image was not found");
            }
        }
        throw new NotFoundException("Category was not found");
    }

    @Override
    public CategoryImageDto get(Long id) throws NotFoundException {
        return new CategoryImageDto(getImage(id));
    }

    @Override
    public List<CategoryImageDto> getAll(PageRequest pageRequest) {
        List<CategoryImageDto> categoryImageDtos = new LinkedList<>();
        Page<CategoryImage> page = categoryImageRepository.findAll(pageRequest);

        page.getContent().forEach(x->categoryImageDtos.add(new CategoryImageDto(x).deflateImage()));

        return categoryImageDtos;
    }

    @Override
    public List<CategoryImageDto> getAll() {
        List<CategoryImageDto> categoryImageDtos = new LinkedList<>();
        categoryImageRepository.findAll().forEach(x->categoryImageDtos.add(new CategoryImageDto(x).deflateImage()));
        return categoryImageDtos;
    }

    public int getSize() {
        return categoryImageRepository.findAll().size();
    }

    @Override
    public CategoryImageDto getLast() {
        return getAll().get(getAll().size()-1);
    }

    @Override
    public CategoryImageDto add(CategoryImageDto dtoEntity) throws NotFoundException, EntityAlreadyExistsException {

        for(CategoryImage image : categoryImageRepository.findAll()) {
            if(image.getCategory().getId().equals(dtoEntity.getCategoryId()))
                throw new EntityAlreadyExistsException("Category image already exists");
        }

        CategoryImage categoryImage = dtoEntity.buildEntity();

        Iterable<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            if(category.getId().equals(dtoEntity.getCategoryId()))
                categoryImage.setCategory(category);
        }

        if(categoryImage.getCategory() == null)
            throw new NotFoundException("Category was not found");

        categoryImageRepository.saveAndFlush(categoryImage);
        categoryImageRepository.refresh(categoryImage);

        return getLast();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        CategoryImage categoryImage = getImage(id);

        categoryImageRepository.delete(categoryImage);
    }

    @Override
    public CategoryImageDto update(Long id, CategoryImageDto dtoEntity) throws NotFoundException {
        delete(id);

        CategoryImage categoryImage = dtoEntity.buildEntity(id);

        categoryImageRepository.saveAndFlush(categoryImage);
        categoryImageRepository.refresh(categoryImage);

        return getLast();
    }
}
