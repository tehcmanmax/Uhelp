package com.tehcman.printers;

import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface IPrintUserProfile {
    String beautify(Long id);
    void printUserRandomDefault(Message msg);
    void addUsersFromCache();
    void addSingleUserFromCache(User user);
    void viewedAllUsers(Message msg);
    void notifyNewProfiles(Message msg);

    List<User> filterUsers(List<User> userList, Status whoToLookFor);
}
