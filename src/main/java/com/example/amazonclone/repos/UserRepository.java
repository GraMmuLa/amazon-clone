package com.example.amazonclone.repos;

import com.example.amazonclone.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends RefreshableRepository<User, Long> {
    public Optional<User> findByUsername(String username);
    public Optional<User> findByPhone(String phone);
    public Optional<User> findByEmail(String email);
}
