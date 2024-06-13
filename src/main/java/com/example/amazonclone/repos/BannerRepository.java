package com.example.amazonclone.repos;

import com.example.amazonclone.models.Banner;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BannerRepository extends RefreshableRepository<Banner, Long> {
    public Optional<Banner> findByUserId(Long userId);
}
