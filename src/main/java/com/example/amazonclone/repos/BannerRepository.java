package com.example.amazonclone.repos;

import com.example.amazonclone.models.Banner;

import java.util.Optional;

public interface BannerRepository extends RefreshableRepository<Banner, Long> {
    public Optional<Banner> findByUserId(Long userId);
}
