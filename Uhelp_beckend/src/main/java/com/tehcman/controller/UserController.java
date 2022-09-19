package com.tehcman.controller;

import com.tehcman.model.User;
import com.tehcman.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

//    @PostMapping("/add")
//    public void add(@RequestParam String tgUserName, String name, String phoneNumber,
//                    String email, String social, String age, String status,
//                    String sex, String city, String country, Integer amountOfPeople,
//                    String date, String additional) {
//
//        User user = new User();
//        user.setTgUsername(tgUserName);
//        user.setName(name);
//        user.setPhoneNumber(phoneNumber);
//        user.setEmail(email);
//        user.setSocial(social);
//        user.setAge(age);
//        user.setStatus(status);
//        user.setSex(sex);
//        user.setCity(city);
//        user.setCountry(country);
//        user.setAmountOfPeople(amountOfPeople);
//        user.setDate(date);
//        user.setAdditional(additional);
//
//        userRepository.save(user);
//    }

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
