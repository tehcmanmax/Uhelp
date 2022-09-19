package com.tehcman.cahce;

import com.tehcman.entities.User;
//import com.tehcman.temp.observer.Observer;
import com.tehcman.services.NewProfileClientNotifier;
import com.tehcman.services.keyboards.profile_search.InlineNoProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserCache implements Cache<User> {

/*    @Getter
    @Setter
    private List<Observer> observers;*/

    private final Map<Long, User> cacheOfAllUsers;


    @Autowired
    public UserCache() {

        this.cacheOfAllUsers = new HashMap<>();
    }


    //notifies that new users are added
    @Override
    public void add(User user) {
//        this.observers.forEach(Observer::update);
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

