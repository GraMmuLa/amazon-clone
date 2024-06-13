package com.example.amazonclone.repos;

import com.example.amazonclone.dto.DiscountTypeDto;
import com.example.amazonclone.models.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountTypeRepository extends RefreshableRepository<DiscountType, Long> {
    public Optional<DiscountType> findByType(String discountTypeName);
}
