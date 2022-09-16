package com.tehcman.controller;

import com.tehcman.model.User;
import com.tehcman.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register/add")
    public void add(@RequestParam String tgUserName, String name, String phoneNumber,
                    String email, String social, String age, String status,
                    String sex, String city, String country, Integer amountOfPeople,
                    String date, String additional) {

        User user = new User();
        user.setTgUsername(tgUserName);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setSocial(social);
        user.setAge(age);
        user.setStatus(status);
        user.setSex(sex);
        user.setCity(city);
        user.setCountry(country);
        user.setAmountOfPeople(amountOfPeople);
        user.setDate(date);
        user.setAdditional(additional);

        userRepository.save(user);
    }

//    @DeleteMapping(path = {"/{id}"})
//    public void remove(@PathVariable("id") Long id) {
//        userRepository.deleteById(id);
//    }

    @GetMapping("/{id}")
    public User findBy(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/")
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
