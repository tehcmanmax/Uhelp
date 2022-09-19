package com.tehcman.obsolete;

import com.tehcman.entities.Phase;
import com.tehcman.entities.User;

import java.util.List;

public interface TableUserCache<T> {
    void add(User user);

    void remove(Long id);

    T findBy(Long id);

    List<T> getAll();

    List<T> filterUsers(Phase phase);

}
