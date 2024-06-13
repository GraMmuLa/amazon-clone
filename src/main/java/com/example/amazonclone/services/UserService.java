package com.example.amazonclone.services;

import com.example.amazonclone.dto.CredentialsDto;
import com.example.amazonclone.dto.SignUpDto;
import com.example.amazonclone.dto.UserDto;
import com.example.amazonclone.exceptions.AuthenticationException;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.ProductColor;
import com.example.amazonclone.models.User;
import com.example.amazonclone.repos.ProductColorRepository;
import com.example.amazonclone.repos.RoleRepository;
import com.example.amazonclone.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements AuthenticationService<UserDto, CredentialsDto, SignUpDto> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductColorRepository productColorRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto findByLogin(String username) throws NotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new NotFoundException("User was not found!"));
        return new UserDto(user);
    }

    @Override
    public UserDto login(CredentialsDto credentialsDto) throws NotFoundException, AuthenticationException {
        User user = userRepository.findByUsername(credentialsDto.getUsername())
                .orElseThrow(()->new NotFoundException("User was not found!"));

        if(passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
            return new UserDto(user);
        }

        throw new AuthenticationException("Invalid password");
    }

    public int getSize() {
        return userRepository.findAll().size();
    }

    public UserDto loginByEmail(CredentialsDto credentialsDto) throws NotFoundException {
        User user = userRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(()->new NotFoundException("User was not found!"));
        credentialsDto.setUsername(user.getUsername());

        return login(credentialsDto);
    }

    public UserDto loginByPhone(CredentialsDto credentialsDto) throws NotFoundException {
        User user = userRepository.findByPhone(credentialsDto.getPhone())
                .orElseThrow(()->new NotFoundException("User was not found!"));
        credentialsDto.setUsername(user.getUsername());

        return login(credentialsDto);
    }

    @Override
    public UserDto register(SignUpDto signUpDto) throws EntityAlreadyExistsException {
        Optional<User> optionalUser;
        if(signUpDto.getUsername() != null)
            optionalUser = userRepository.findByUsername(signUpDto.getUsername());
        else if (signUpDto.getEmail() != null)
            optionalUser = userRepository.findByEmail(signUpDto.getEmail());
        else
            optionalUser = userRepository.findByPhone(signUpDto.getPhone());

        if(optionalUser.isPresent())
            throw new EntityAlreadyExistsException("User already exists");

        User user = signUpDto.buildEntity();

        user.setRole(roleRepository.findByRoleName(signUpDto.getRoleName()));

        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        User saved = userRepository.saveAndFlush(user);
        userRepository.refresh(user);

        return new UserDto(saved);
    }

    public List<UserDto> getAll() {
        List<UserDto> userDtos = new ArrayList<>();

        userRepository.findAll().forEach(x->userDtos.add(new UserDto(x)));

        return userDtos;
    }

    public UserDto getById(Long id) throws NotFoundException {
        return new UserDto(userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User was not found!")));
    }

    public UserDto addFavouriteProductColor(Long userId, Long productColorId) throws NotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException("User was not found!"));
        ProductColor productColor = productColorRepository.findById(productColorId)
                .orElseThrow(()->new NotFoundException("ProductColor was not found!"));
        user.getFavouriteProductColors().add(productColor);

        userRepository.saveAndFlush(user);
        userRepository.refresh(user);

        return new UserDto(user);
    }

    public UserDto deleteFavouriteProductColor(Long userId, Long productColorId) throws NotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException("User was not found!"));
        ProductColor productColor = productColorRepository.findById(productColorId)
                .orElseThrow(()->new NotFoundException("ProductColor was not found!"));
        user.getFavouriteProductColors().remove(productColor);

        userRepository.saveAndFlush(user);
        userRepository.refresh(user);

        return new UserDto(user);
    }
}
