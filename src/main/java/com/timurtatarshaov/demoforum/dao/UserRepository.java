package com.timurtatarshaov.demoforum.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timurtatarshaov.demoforum.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(final String username);

}
