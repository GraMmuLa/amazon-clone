package com.example.amazonclone.services;

import com.example.amazonclone.dto.SubcategoryDto;
import com.example.amazonclone.dto.SubcategoryImageDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.Category;
import com.example.amazonclone.models.Subcategory;
import com.example.amazonclone.repos.CategoryRepository;
import com.example.amazonclone.repos.ProductRepository;
import com.example.amazonclone.repos.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class SubcategoryService implements JpaService<SubcategoryDto, Subcategory, Long> {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final SubcategoryImageService subcategoryImageService;

    @Autowired
    public SubcategoryService(SubcategoryRepository subcategoryRepository,
                              CategoryRepository categoryRepository,
                              ProductRepository productRepository,
                              SubcategoryImageService subcategoryImageService) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.subcategoryImageService = subcategoryImageService;
    }

    private Subcategory getSubcategory(Long id) throws NotFoundException {
        Iterable<Subcategory> subcategories = subcategoryRepository.findAll();

        for (Subcategory subcategory : subcategories) {
            if(subcategory.getId().equals(id))
                return subcategory;
        }

        throw new NotFoundException("Subcategory was not found");
    }

    @Override
    public SubcategoryDto get(Long id) throws NotFoundException {
        return new SubcategoryDto(getSubcategory(id));
    }

    @Override
    public List<SubcategoryDto> getAll(PageRequest pageRequest) {
        List<SubcategoryDto> subcategoriesDtos = new LinkedList<>();
        Page<Subcategory> page = subcategoryRepository.findAll(pageRequest);

        page.getContent().forEach(x->subcategoriesDtos.add(new SubcategoryDto(x)));

        return subcategoriesDtos;
    }

    @Override
    public List<SubcategoryDto> getAll() {
        List<SubcategoryDto> subcategoriesDtos = new LinkedList<>();

        subcategoryRepository.findAll().forEach(x->subcategoriesDtos.add(new SubcategoryDto(x)));

        return subcategoriesDtos;
    }

    public int getSize() {
        return subcategoryRepository.findAll().size();
    }

    @Override
    public SubcategoryDto getLast() {
        return getAll().get(getAll().size()-1);
    }

    @Override
    public SubcategoryDto add(SubcategoryDto dtoEntity) throws NotFoundException {
        Subcategory subcategory = dtoEntity.buildEntity();

        Iterable<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            if(category.getId().equals(dtoEntity.getCategoryId()))
                subcategory.setCategory(category);
        }

        if(subcategory.getCategory() == null)
            throw new NotFoundException("Category was not found");

        subcategoryRepository.saveAndFlush(subcategory);
        subcategoryRepository.refresh(subcategory);

        return getLast();
    }

    public SubcategoryDto addWithImage(MultipartFile file, SubcategoryDto dtoEntity) throws IOException, NotFoundException, EntityAlreadyExistsException {
        SubcategoryDto responseDto = add(dtoEntity);
        subcategoryImageService.add(new SubcategoryImageDto(file, responseDto.getId()));
        return responseDto;
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Subcategory subcategory = getSubcategory(id);

        subcategoryRepository.delete(subcategory);
    }

    @Override
    public SubcategoryDto update(Long id, SubcategoryDto dtoEntity) throws NotFoundException {
        delete(id);

        Subcategory subcategory = dtoEntity.buildEntity(id);

        subcategoryRepository.saveAndFlush(subcategory);
        subcategoryRepository.refresh(subcategory);

        return getLast();
    }
}
