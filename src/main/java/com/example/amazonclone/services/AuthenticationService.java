package com.example.amazonclone.services;

import com.example.amazonclone.exceptions.AuthenticationException;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;

public interface AuthenticationService<Dto, CredentialsDto, SignUpDto> {
    public Dto login(CredentialsDto credentialsDto) throws NotFoundException, AuthenticationException;
    public Dto register(SignUpDto signUpDto) throws EntityAlreadyExistsException;
}
