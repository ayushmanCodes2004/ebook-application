package com.ebook.Auth_Service.Service;

import com.ebook.Auth_Service.DTO.UserDTO;
import com.ebook.Auth_Service.Entity.UserCredential;
import com.ebook.Auth_Service.Repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserCredentialRepository userCredentialRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public String registerUser(UserDTO user){

        UserCredential userCredential = new UserCredential();
        userCredential.setPassword(passwordEncoder.encode(user.getPassword()));
        userCredential.setUserName(user.getUserName());
        userCredential.setEmail(user.getEmail());

        userCredentialRepository.save(userCredential);
        return "User registered successfully";
    }

    public String generateToken(String userName){
        return jwtService.generateToken(userName);

    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

}
