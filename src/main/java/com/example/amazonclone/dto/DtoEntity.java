package com.example.amazonclone.dto;

public interface DtoEntity<T, Id> {
    public T buildEntity();
    public T buildEntity(Id id);
}
