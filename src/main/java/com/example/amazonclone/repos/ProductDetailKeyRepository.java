package com.example.amazonclone.repos;

import com.example.amazonclone.models.ProductDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailKeyRepository extends RefreshableRepository<ProductDetailKey, Long> {
}
