package com.superdrive.services;


import com.superdrive.entity.User;
import com.superdrive.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private UserMapper userMapper;
    private HashService hashService;


    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    // check if a username is available for signup
    public boolean isUsernameAvailable (String username){

        return (userMapper.getUser(username) == null );
    }


    // create a new user into the database
    public int createUser(User user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];

        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        String hashedPassword = hashService.getHashedValue(user.getPassword(),encodedSalt);

        return userMapper.insert(new User(null,user.getUsername(),encodedSalt,hashedPassword,user.getFirstName(),user.getLastName()));

    }

    // get a user object by username
    public User getUser(String username){
        return userMapper.getUser(username);
    }
}
