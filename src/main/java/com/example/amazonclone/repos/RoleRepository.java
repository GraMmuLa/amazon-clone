package com.example.amazonclone.repos;

import com.example.amazonclone.models.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends RefreshableRepository<Role, Long> {
    public Role findByRoleName(String roleName);
}
