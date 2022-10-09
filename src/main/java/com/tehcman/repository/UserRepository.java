package com.tehcman.repository;

import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByCityAndStatus(String city, Status status);
    Optional<User> findByChatId(Long id);
}
