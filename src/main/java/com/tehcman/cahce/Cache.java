package com.tehcman.cahce;

import com.tehcman.entities.User;

import java.util.List;

public interface Cache <T>{
   void add(User user);
   void remove(Long id);
   T findBy(Long id);
   List<T> getAll();

}
