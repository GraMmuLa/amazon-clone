package com.example.amazonclone.repos;

import com.example.amazonclone.models.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypeRepository extends RefreshableRepository<ProductType, Long> {
    @Query("SELECT pr FROM ProductType pr WHERE pr.subcategory.id = :subcategoryId")
    public List<ProductType> findProductTypesBySubcategoryId(@Param("subcategoryId") Long subcategoryId);
}
