package com.example.amazonclone.services;

import com.example.amazonclone.dto.BannerDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.Banner;
import com.example.amazonclone.models.User;
import com.example.amazonclone.repos.BannerRepository;
import com.example.amazonclone.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BannerService implements JpaService<BannerDto, Banner, Long> {
    private final BannerRepository bannerRepository;
    private final UserRepository userRepository;

    @Override
    public BannerDto get(Long id) throws NotFoundException {
        return new BannerDto(bannerRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Banner was not found!"))).deflateImage();
    }

    @Override
    public List<BannerDto> getAll(PageRequest pageRequest) {
        List<BannerDto> bannerDtos = new ArrayList<>();

        bannerRepository.findAll(pageRequest)
                .getContent().forEach(banner->bannerDtos.add(new BannerDto(banner).deflateImage()));

        return bannerDtos;
    }

    @Override
    public List<BannerDto> getAll() {

        List<BannerDto> bannerDtos = new ArrayList<>();

        bannerRepository.findAll().forEach(banner->bannerDtos.add(new BannerDto(banner).deflateImage()));

        return bannerDtos;
    }

    public BannerDto getByUser(Long userId) throws NotFoundException {
        return new BannerDto(bannerRepository.findByUserId(userId)
                .orElseThrow(()->new NotFoundException("Banner was not found!"))).deflateImage();
    }

    @Override
    public BannerDto getLast() {
        List<BannerDto> bannerDtos = getAll();
        return bannerDtos.get(bannerDtos.size()-1).deflateImage();
    }

    @Override
    public BannerDto add(BannerDto dtoEntity) throws NotFoundException, EntityAlreadyExistsException {

        if (bannerRepository.findByUserId(dtoEntity.getUserId()).isPresent())
            throw new EntityAlreadyExistsException("Banner with this user id is present!");

        Banner banner = dtoEntity.buildEntity();

        for (User user : userRepository.findAll()) {
            if(dtoEntity.getUserId().equals(user.getId())) {
                banner.setUser(user);
                bannerRepository.saveAndFlush(banner);
                bannerRepository.refresh(banner);
                return getLast().deflateImage();
            }
        }
        throw new NotFoundException("Seller(user) was not found!");
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        bannerRepository.deleteById(id);
    }

    @Override
    public BannerDto update(Long id, BannerDto dtoEntity) throws NotFoundException {
        delete(id);

        Banner banner = dtoEntity.buildEntity(id);

        bannerRepository.saveAndFlush(banner);
        bannerRepository.refresh(banner);

        return new BannerDto(banner).deflateImage();
    }
}
