package com.example.amazonclone.repos;

import com.example.amazonclone.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends RefreshableRepository<Discount, Long> {
}
