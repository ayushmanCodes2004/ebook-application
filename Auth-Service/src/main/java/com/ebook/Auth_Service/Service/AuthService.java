package com.ebook.Auth_Service.Service;

import com.ebook.Auth_Service.DTO.UserDTO;
import com.ebook.Auth_Service.DTO.UserResponseDTO;
import com.ebook.Auth_Service.Entity.UserCredential;
import com.ebook.Auth_Service.Exception.InvalidCustomerIdException;
import com.ebook.Auth_Service.Repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserCredentialRepository userCredentialRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final EmailPublisher emailPublisher;

    public String registerUser(UserDTO user){

        Random random = new Random();
        int number = 1000 + random.nextInt(9000);
        String customerId = "CUST-" + number;

        UserCredential userCredential = new UserCredential();
        userCredential.setPassword(passwordEncoder.encode(user.getPassword()));
        userCredential.setUserName(user.getUserName());
        userCredential.setEmail(user.getEmail());
        userCredential.setCustomerId(customerId);

        userCredentialRepository.save(userCredential);

        String subject = "Welcome to E-Book Platform!";
        String body = "Dear " + userCredential.getUserName() + ",\n\n"
                + "Welcome to the E-Book Store! 🎉\n\n"
                + "We are thrilled to have you join our community of passionate readers.\n\n"
                + "Your Customer ID is: " + customerId + "\n\n"
                + "With your new account, you can now explore thousands of e-books across various genres, "
                + "manage your personal library, and enjoy seamless reading anytime, anywhere.\n\n"
                + "Here are a few things you can do right away:\n"
                + "• Browse our wide collection of e-books.\n"
                + "• Add your favorite titles to your wishlist or library.\n"
                + "• Track your orders and manage your purchases with ease.\n"
                + "• Stay updated with our latest releases and special offers.\n\n"
                + "Your journey into the world of endless stories, knowledge, and imagination begins here. "
                + "We’re committed to making your reading experience delightful and enriching.\n\n"
                + "If you have any questions or need assistance, our support team is always ready to help you.\n\n"
                + "Happy Reading! 📚\n"
                + "Warm Regards,\n"
                + "The E-Book Team";


        emailPublisher.publishEmailMessage(userCredential.getEmail(), subject, body);

        return "User registered successfully with customerId: " + customerId;

    }

    public String generateToken(String userName){
        return jwtService.generateToken(userName);

    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

    public UserResponseDTO validCustomerId(String customerId){
        Optional<UserCredential> userCredential =  userCredentialRepository.findByCustomerId(customerId);
        if(userCredential.isEmpty()){
            throw new InvalidCustomerIdException("Invalid Customer ID: " + customerId);
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setCustomerId(userCredential.get().getCustomerId());
        userResponseDTO.setUserName(userCredential.get().getUserName());
        userResponseDTO.setEmail(userCredential.get().getEmail());

        return userResponseDTO;


    }

}
