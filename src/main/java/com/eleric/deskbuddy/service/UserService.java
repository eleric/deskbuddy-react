package com.eleric.deskbuddy.service;

import com.eleric.deskbuddy.pojo.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	public User retrieveUser(String username) {
		return new User(username);
	}
}
