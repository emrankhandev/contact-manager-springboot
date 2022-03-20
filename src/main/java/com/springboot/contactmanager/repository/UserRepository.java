package com.springboot.contactmanager.repository;

import com.springboot.contactmanager.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    
}
