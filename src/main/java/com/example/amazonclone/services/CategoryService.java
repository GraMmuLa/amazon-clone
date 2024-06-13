package com.example.amazonclone.services;

import com.example.amazonclone.dto.CategoryDto;
import com.example.amazonclone.dto.CategoryImageDto;
import com.example.amazonclone.dto.SubcategoryDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.Category;
import com.example.amazonclone.models.Subcategory;
import com.example.amazonclone.repos.CategoryRepository;
import com.example.amazonclone.repos.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements JpaService<CategoryDto, Category, Long> {
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryImageService categoryImageService;

    @Autowired
    public CategoryService(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository, CategoryImageService categoryImageService) {
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.categoryImageService = categoryImageService;
    }

    private Category getCategory(Long id) throws NotFoundException {
        Iterable<Category> categories = categoryRepository.findAll();

        for(Category category : categories)
            if(id.equals(category.getId()))
                return category;
        throw new NotFoundException("Category was not found");
    }

    @Override
    public List<CategoryDto> getAll(PageRequest pageRequest) {
        List<CategoryDto> categoryDtos = new LinkedList<>();
        Page<Category> page = categoryRepository.findAll(pageRequest);

        page.getContent().forEach(x -> categoryDtos.add(new CategoryDto(x)));

        return categoryDtos;
    }

    @Override
    public List<CategoryDto> getAll() {
        List<CategoryDto> categoryDtos = new LinkedList<>();

        categoryRepository.findAll().forEach(x -> categoryDtos.add(new CategoryDto(x)));

        return categoryDtos;
    }

    public int getSize() {
        return categoryRepository.findAll().size();
    }

    public Long getId(CategoryDto categoryDto) throws NotFoundException {
        for(Category category : categoryRepository.findAll())
            if(category.getName().equals(categoryDto.getName()))
                return category.getId();
        throw new NotFoundException("Category was not found");
    }

    @Override
    public CategoryDto get(Long id) throws NotFoundException {
        return new CategoryDto(getCategory(id));
    }

    public List<SubcategoryDto> getSubcategories(Long id) throws NotFoundException {
        Category category = getCategory(id);

        List<SubcategoryDto> subcategoriesDto = new LinkedList<>();

        if(category.getSubcategories() != null) {
            subcategoryRepository.findAll().forEach(x -> {
                Optional<Subcategory> subcategory = subcategoryRepository.findById(x.getId());
                if(subcategory.isPresent() && category.getSubcategories().contains(subcategory.get()))
                    subcategoriesDto.add(new SubcategoryDto(x));
            });
        }

        return subcategoriesDto;
    }

    @Override
    public CategoryDto getLast() {
        return getAll().get(getAll().size()-1);
    }

    @Override
    public CategoryDto add(CategoryDto dtoEntity) {
        Category category = dtoEntity.buildEntity();

        categoryRepository.saveAndFlush(category);
        categoryRepository.refresh(category);

        return getLast();
    }

    public CategoryDto addWithImage(MultipartFile file, CategoryDto categoryDto) throws IOException, NotFoundException, EntityAlreadyExistsException {
        CategoryDto responseDto = add(categoryDto);
        categoryImageService.add(new CategoryImageDto(file, responseDto.getId()));
        return responseDto;
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Category category = getCategory(id);

        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto dtoEntity) throws NotFoundException {
        delete(id);

        Category category = dtoEntity.buildEntity(id);

        categoryRepository.saveAndFlush(category);
        categoryRepository.refresh(category);

        return getLast();
    }
}
