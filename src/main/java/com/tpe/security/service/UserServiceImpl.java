package com.tpe.security.service;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService {

    /*
        In this class we goring to convert:
            1. User to UserDetail
            2. Role to GrantedAuthority
     */

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //finding username from our database
        User foundUser = userRepository.findByUserName(username).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with username: "+username)
        );


        if(foundUser!=null){
            return new org.
                    springframework.
                    security.
                    core.
                    userdetails.
                    User(foundUser.getUserName(),
                    foundUser.getPassword(),
                    buildGrantedAuthority(foundUser.getRoles())
            );
        }else{
            throw  new UsernameNotFoundException("User not found : " +username  );
        }


    }
    private static List<SimpleGrantedAuthority> buildGrantedAuthority(final Set<Role> roles){

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role:  roles){
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }
        return authorities;

    }
}