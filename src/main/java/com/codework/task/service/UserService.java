package com.codework.task.service;

import com.codework.task.entity.User;

public interface UserService {

    public User saveUser(User user);
    public boolean existEmailCheck(String email);

    public void removeUser(int id);

    public User getUserName(int id, String name);
    
    public User findByEmail(String email);

}
    
