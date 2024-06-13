package com.example.amazonclone.dto;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredentialsDto {
    @Nullable
    private String username;
    @Nullable
    private String email;
    @Nullable
    private String phone;
    private String password;

    public static CredentialsDto fromPhone(String phone, String password) {

        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setPhone(phone);
        credentialsDto.setPassword(password);

        return credentialsDto;
    }

    public static CredentialsDto fromUsername(String username, String password) {

        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setUsername(username);
        credentialsDto.setPassword(password);

        return credentialsDto;
    }

    public static CredentialsDto fromEmail(String email, String password) {
        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setEmail(email);
        credentialsDto.setPassword(password);

        return credentialsDto;
    }
}
