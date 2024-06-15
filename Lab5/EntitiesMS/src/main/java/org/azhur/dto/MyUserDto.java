package org.azhur.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyUserDto {
    private Integer id;
    private String email;
    private String password;
    private String role;
    private OwnerDto ownerDto;

    public MyUserDto(String username, String password, String role, OwnerDto ownerDto) {
        this.email = username;
        this.password = password;
        this.role = role;
        this.ownerDto = ownerDto;
    }

}

