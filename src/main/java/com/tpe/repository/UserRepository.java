package com.tpe.repository;

import com.tpe.domain.User;
import com.tpe.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //we have override findById method to findByUserName
    Optional<User> findByUserName(String username) throws ResourceNotFoundException;
}