package com.codework.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.codework.task.entity.User;
import com.codework.task.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public User saveUser(User user) {
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
       
    }

    @Override
    public boolean existEmailCheck(String email) {
        return userRepository.existsByEmail(email);
        
    }


    @Override
    public void removeUser(int id) {
        userRepository.deleteById(id);  
    }


    public void removeSessionMessage(){
        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        session.removeAttribute("msg");
    }

    @Override
    public User getUserName(int id, String name) {
        User username = userRepository.findById(id).get();
        return username;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
}
