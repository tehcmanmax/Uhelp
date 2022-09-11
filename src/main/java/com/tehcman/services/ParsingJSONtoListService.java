package com.tehcman.services;
/*Jackson ObjectMapper library is used
 *
 * */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tehcman.entities.User;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

//@Service
public class ParsingJSONtoListService {
    public List<User> parse() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<User> users = objectMapper.readValue(new File("client.json"), new TypeReference<List<User>>() {
            });
            return users;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
