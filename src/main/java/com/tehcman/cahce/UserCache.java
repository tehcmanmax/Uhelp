package com.tehcman.cahce;

import com.tehcman.entities.User;
//import com.tehcman.temp.observer.Observer;
import com.tehcman.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class UserCache implements Cache<User> {

    private final UserRepository userRepository;


    @Autowired
    public UserCache(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //notifies that new users are added
    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        Long userId = userRepository.findByChatId(id).get().getId();
        userRepository.deleteById(userId);
    }

    @Override
    public User findBy(Long id) {
        return userRepository.findByChatId(id).orElse(null);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}

