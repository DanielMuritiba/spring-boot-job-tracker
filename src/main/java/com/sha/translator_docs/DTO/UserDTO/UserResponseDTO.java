package com.sha.translator_docs.DTO.UserDTO;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String token;

}
