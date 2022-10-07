package com.tehcman.controller;

import com.tehcman.model.User;
import com.tehcman.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String searchHost(Model model, @RequestParam(name = "city", defaultValue = "") String city) {
        model.addAttribute("city", !city.equals(""));
        model.addAttribute("users", userRepository.findByCityAndStatus(city, "host"));
        return "search";
    }

    @GetMapping("/register")
    public String newRegistration(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String saveRegistration(@ModelAttribute("user") User user,
                                   BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "register";//will redirect to viewemp request mapping
        }
        userRepository.save(user);
        //redirectAttributes.addFlashAttribute("message", "Student " + student.getFirstName()+" "+student.getLastName() + " saved");
        return "redirect:/register";//will redirect to viewemp request mapping
    }
}