package com.codework.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codework.task.config.CustomUser;
import com.codework.task.entity.User;
import com.codework.task.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByEmail(username);

        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }
        else {
            System.out.println("user is found" + user);
            return new CustomUser(user);  
        }
    }
}
