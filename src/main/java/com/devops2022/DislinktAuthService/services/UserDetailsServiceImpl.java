package com.devops2022.DislinktAuthService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devops2022.DislinktAuthService.model.User;
import com.devops2022.DislinktAuthService.model.VerificationToken;
import com.devops2022.DislinktAuthService.repositories.UserRepository;
import com.devops2022.DislinktAuthService.repositories.VerificationTokenRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository absUserRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = absUserRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));

        return user;
    }

    public User loadUserById(Integer id) {
        User user = absUserRepository.findById(id).orElse(null);
        return user;
    }

    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public void saveRegisteredUser(User user) {
        absUserRepository.save(user);
    }

    public VerificationToken createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        return tokenRepository.save(myToken);
    }
}
