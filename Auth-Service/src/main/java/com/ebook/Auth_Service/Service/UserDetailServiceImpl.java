package com.ebook.Auth_Service.Service;

import com.ebook.Auth_Service.Entity.UserCredential;
import com.ebook.Auth_Service.Repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserCredential> fetchedUser = userCredentialRepository.findByUserName(username);
        if(fetchedUser.isEmpty()){
            throw new UsernameNotFoundException("User not found in db");
        }


        return new UserPrincipal(fetchedUser.get());

    }
}
