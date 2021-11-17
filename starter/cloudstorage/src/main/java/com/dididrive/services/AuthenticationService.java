package com.dididrive.services;


import com.dididrive.entity.User;
import com.dididrive.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {

    UserMapper userMapper;
    HashService hashService;

    public AuthenticationService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println("username is "+username );
        System.out.println("password is "+password);

        User user = userMapper.getUser(username);
        if(user != null){

            String hashedPassword = hashService.getHashedValue(password,user.getSalt());
            System.out.println("hashed password is "+hashedPassword);
            System.out.println("stored password in the db is "+user.getPassword());
            if(user.getPassword().equals(hashedPassword)){
                return new UsernamePasswordAuthenticationToken(username,password,new ArrayList<>());
            }
        }

        return null;
    }





    @Override
    public boolean supports(Class<?> authentication) {

        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
