package com.example.amazonclone.dto;

import com.example.amazonclone.models.User;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SignUpDto extends UserDto {
    private String password;
}
