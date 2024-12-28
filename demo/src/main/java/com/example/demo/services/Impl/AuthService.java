
package com.example.demo.services.Impl;

import com.example.demo.Entities.Customer;
import com.example.demo.Entities.Seller;
import com.example.demo.Entities.User;
import com.example.demo.Repositories.UserRoleRepository;
import com.example.demo.Repositories.UserRepository;
import org.example.input.UserRegisterInputModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.Entities.enums.UserRoles;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;

    private UserRoleRepository userRoleRepository;


    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    public void register(UserRegisterInputModel registrationDTO) {
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new RuntimeException("passwords.match");
        }

        Optional<User> byEmail = this.userRepository.findByEmail(registrationDTO.getEmail());

        if (byEmail.isPresent()) {
            throw new RuntimeException("email.used");
        }
        var userRole = userRoleRepository.
                findRoleByName(UserRoles.SELLER).orElseThrow();
        Seller seller = new Seller(
                registrationDTO.getUsername(),
                registrationDTO.getEmail(),
                passwordEncoder.encode(registrationDTO.getPassword())
        );
        seller.setRoles(List.of(userRole));

        this.userRepository.save(seller);
        if (registrationDTO.getRole().equals("Покупатель")) {
            userRole = userRoleRepository.
                    findRoleByName(UserRoles.CUSTOMER).orElseThrow();
            Customer customer = new Customer(
                    registrationDTO.getUsername(),
                    registrationDTO.getEmail(),
                    passwordEncoder.encode(registrationDTO.getPassword())


            );
            customer.setRoles(List.of(userRole));

            this.userRepository.save(customer);
        }




    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
    }
}
