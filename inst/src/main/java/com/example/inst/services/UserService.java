package com.example.inst.services;

import com.example.inst.dto.UserDTO;
import com.example.inst.entity.User;
import com.example.inst.entity.enums.ERole;
import com.example.inst.exeptions.UserExistException;
import com.example.inst.payload.request.SignupRequest;
import com.example.inst.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;


@Service
public class UserService {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest userIn){
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try{
            LOGGER.info("Saving User {}", userIn.getEmail());
            return userRepository.save(user);
        }catch (Exception e){
            LOGGER.error("Error: " + e.getMessage());
            throw new UserExistException("The user "+ user.getEmail()+ "is already exist");
        }

    }

    public User updateUser(UserDTO userDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        user.setName(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setBio(userDTO.getBio());

        return userRepository.save(user);

    }

    public User getCurrentUser(Principal principal){
         return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
