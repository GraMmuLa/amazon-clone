package com.example.amazonclone.services;

import com.example.amazonclone.dto.DtoEntity;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;

public interface JpaService<Dto extends DtoEntity<Entity, Id>, Entity, Id> {
    public Dto get(Id id) throws NotFoundException, IOException;
    public List<Dto> getAll(PageRequest pageRequest);
    public List<Dto> getAll();
    public Dto getLast();
    public Dto add(Dto dtoEntity) throws NotFoundException, EntityAlreadyExistsException;
    public void delete(Id id) throws NotFoundException;
    public Dto update(Id id, Dto dtoEntity) throws NotFoundException;
}