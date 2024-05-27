package com.example.amazonclone.repos;

import com.example.amazonclone.models.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountTypeRepository extends RefreshableRepository<DiscountType, Long> {
}
