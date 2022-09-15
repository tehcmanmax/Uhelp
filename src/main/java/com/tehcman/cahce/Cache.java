package com.tehcman.cahce;

import com.tehcman.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public interface Cache extends JpaRepository<User, Long> {
//   void add(User user);
//   void remove(Long id);
//   User findBy(Long id);
//   List<User> getAll();

}
