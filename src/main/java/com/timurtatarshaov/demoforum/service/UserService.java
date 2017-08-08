package com.timurtatarshaov.demoforum.service;

import com.timurtatarshaov.demoforum.model.User;

public interface UserService {
	void save(User user);

    User findByUsername(String username);
}
