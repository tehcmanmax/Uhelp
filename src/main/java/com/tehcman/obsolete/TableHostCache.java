package com.tehcman.obsolete;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TableHostCache implements Cache<User> {
    private final Map<Long, User> filteredRefugee;

    public TableHostCache() {
        this.filteredRefugee = new HashMap<>();
    }


    @Override
    public void add(User user) {
        filteredRefugee.putIfAbsent(user.getId(), user);
    }

    @Override
    public void remove(Long id) {
        filteredRefugee.remove(id);
    }

    @Override
    public User findBy(Long id) {
        return filteredRefugee.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(filteredRefugee.values());
    }
}
