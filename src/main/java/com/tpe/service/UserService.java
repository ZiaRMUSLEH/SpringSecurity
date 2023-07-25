package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.domain.enums.UserRole;
import com.tpe.dto.UserRequest;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    public void saveUser(UserRequest userRequest) {
        User newUser  = new User();
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastName(userRequest.getLastName());
        newUser.setUserName(userRequest.getUserName());

        //we should not store plain password to db.. we have to encode
        //newUser.setPassword(newUser.getPassword());

        String password = userRequest.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);

        // we need to set Role for the user
        Role role = roleService.getRoleByType(UserRole.ROLE_ADMIN); //we set default role as Admin

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        newUser.setRoles(roles);

        userRepository.save(newUser );


    }
}