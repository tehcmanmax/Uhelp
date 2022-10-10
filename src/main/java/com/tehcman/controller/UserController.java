package com.tehcman.controller;

import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String searchHost(Model model, @RequestParam(name = "city", defaultValue = "") String city) {
        model.addAttribute("city", !city.equals(""));
        model.addAttribute("users", userRepository.findByCityAndStatus(city, Status.HOST));
        return "search";
    }

    @GetMapping("/register")
    public String newRegistration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String saveRegistration(@ModelAttribute("user") User user, @ModelAttribute("status") String status) {
        user.setStatus(Status.valueOf(status));
        userRepository.save(user);
        return "redirect:/register";
    }

    @GetMapping("/user/{id}")
    public String readUser(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/user/update/{id}")
    public String updateUserInfo(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user, @ModelAttribute("status") String status) {
        user.setChatId(userRepository.findById(id).get().getChatId());
        user.setStatus(Status.valueOf(status));
        userRepository.save(user);
        return "redirect:/";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/";
    }
}