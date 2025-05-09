package com.sha.translator_docs.controller;

import com.sha.translator_docs.DTO.UserDTO.UserMapper;
import com.sha.translator_docs.DTO.UserDTO.UserRequestDTO;
import com.sha.translator_docs.DTO.UserDTO.UserResponseDTO;
import com.sha.translator_docs.model.User;
import com.sha.translator_docs.service.AuthenticationService;
import com.sha.translator_docs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    //Save User
    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserRequestDTO userDTO){
        if(userService.findByEmail(userDTO.getEmail()).isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        User userToSave = UserMapper.toEntity(userDTO);
        User savedUser = userService.saveUser(userToSave);
        UserResponseDTO responseDTO = UserMapper.toDTO(savedUser);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    //Log In User
    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody UserRequestDTO userDTO){
        User loginAttempt = new User();
        loginAttempt.setEmail(userDTO.getEmail());
        loginAttempt.setPassword(userDTO.getPassword());

        User signedInUser = authenticationService.signInAndReturnJWT(loginAttempt);
        UserResponseDTO responseDTO = UserMapper.toDTO(signedInUser);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}