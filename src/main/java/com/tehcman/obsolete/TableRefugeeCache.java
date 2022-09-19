package com.tehcman.obsolete;

import com.tehcman.entities.Phase;
import com.tehcman.entities.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TableRefugeeCache implements TableUserCache<User> {
    private final List<User> filteredRefugee;

    public TableRefugeeCache() {
        this.filteredRefugee = new ArrayList<>();
    }

    @Override
    public void add(User user) {
        filteredRefugee.add(user);
    }

    @Override
    public void remove(Long id) {
        int index = 0;
        for (User user : this.filteredRefugee){
            index++;
            if (user.getId().equals(id)){
                this.filteredRefugee.remove(index);
                return;
            }
        }
    }

    @Override
    public User findBy(Long id) {
        for (User user : this.filteredRefugee){
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return this.filteredRefugee;
    }

    @Override
    public List<User> filterUsers(Phase phase) {
        return null;
    }
}
