package com.example.amazonclone.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RefreshableRepository<Entity, Id> extends JpaRepository<Entity, Id> {
    public void refresh(Entity entity);
}
