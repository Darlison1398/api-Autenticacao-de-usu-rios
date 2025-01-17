package com.auth.demo.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.auth.demo.model.AdminModel;
import com.auth.demo.model.UserModel;
import com.auth.demo.repository.AdminRepository;
import com.auth.demo.repository.UserRepository;

@Component
public class UserCustomDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<AdminModel> admin = adminRepository.findByEmail(username);
        if (admin.isPresent()) {
            return admin.get();
        }

        Optional<UserModel> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return user.get();
        }

        throw new UsernameNotFoundException("User not found with username: " + username);

    }
    
}
