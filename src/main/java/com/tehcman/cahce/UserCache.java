package com.tehcman.cahce;

import com.tehcman.entities.User;
import lombok.Getter;
import org.springframework.stereotype.Component;
import com.tehcman.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserCache implements Cache<User> {

    @Getter
    private final List<Observer>;

    private final Map<Long, User> cacheOfAllUsers;

    public UserCache() {
        this.cacheOfAllUsers = new HashMap<>();
        observers = new ArrayList<>();
    }


    @Override
    public void add(User user) {
        cacheOfAllUsers.putIfAbsent(user.getId(), user);
    }

    @Override
    public void remove(Long id) {
        cacheOfAllUsers.remove(id);
    }

    @Override
    public User findBy(Long id) {
        return cacheOfAllUsers.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(cacheOfAllUsers.values());
    }
}

