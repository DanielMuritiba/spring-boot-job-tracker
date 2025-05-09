package com.sha.translator_docs.DTO.UserDTO;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String username;
    private String email;
    private String password;
    private String role;
}
