package com.tehcman.cahce;

import com.tehcman.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@Component
public class UserCache {
//    private final Map<Long, User> cacheOfAllUsers;
    @Autowired
    private final Cache userRepository;

    public UserCache(Cache userRepository) {
        this.userRepository = userRepository;
    }

//    public UserCache() {
//        this.cacheOfAllUsers = new HashMap<>();
//    }


@PostMapping
    public void add(@RequestBody User user) {
//        cacheOfAllUsers.putIfAbsent(user.getId(), user);
        userRepository.save(user);
    }

    @DeleteMapping(path = {"/{id}"})
    public void remove(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
    }

    @GetMapping(path = {"/{id}"})
    public User findBy(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<User> getAll() {
//        return new ArrayList<>(cacheOfAllUsers.values());
        return userRepository.findAll();
    }
}

