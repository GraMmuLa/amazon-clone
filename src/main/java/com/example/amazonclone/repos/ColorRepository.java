package com.example.amazonclone.repos;

import com.example.amazonclone.models.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends RefreshableRepository<Color, Long> {
}
