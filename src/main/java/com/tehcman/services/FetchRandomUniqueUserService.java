package com.tehcman.services;

import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.printers.HostProfile;
import com.tehcman.printers.RefugeeProfile;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public class FetchRandomUniqueUserService {
    private final UserCache userCache;

    private HostProfile hostProfile;
    private RefugeeProfile refugeeProfile;
    private int prevNumber = -1;
    @Getter
    private int randNumb;

    @Autowired
    public FetchRandomUniqueUserService(UserCache userCache) {
        this.userCache = userCache;
    }

    public User fetchRandomUniqueUser(Status userType) {
        if (userType.equals(Status.HOST)) {
            int size = this.hostProfile.getHosts().size();
            int count = 0;
            for (User user : this.hostProfile.getHosts()) {
                if (user.isViewed()) {
                    count++;
                }
                if (count == size) {
                    //show message that there is no more profiles left; new inline message
//                    throw new ArrayStoreException("ran out of profiles"); //temp
                    return null; //if all viewed return null
                }
            }

            this.randNumb = (int) (Math.random() * this.hostProfile.getHosts().size());
            while (this.randNumb == prevNumber) {
                this.randNumb = (int) (Math.random() * this.hostProfile.getHosts().size());
            }
            prevNumber = this.randNumb;

            //check if unique
            while (this.hostProfile.getHosts().get(this.randNumb).isViewed()) {
                this.randNumb = (int) (Math.random() * this.hostProfile.getHosts().size());
                while (this.randNumb == prevNumber) {
                    this.randNumb = (int) (Math.random() * this.hostProfile.getHosts().size());
                }
                prevNumber = this.randNumb;
            }

//            setIsViewed(this.hostProfile.getHosts().get(this.randNumb).getId(), Status.HOST);

            return this.hostProfile.getHosts().get(this.randNumb);
        } else if (userType.equals(Status.REFUGEE)){
            var refugees = this.refugeeProfile.getRefugees();

            int size = refugees.size();
            int count = 0;
            for (User user : refugees) {
                if (user.isViewed()) {
                    count++;
                }
                if (count == size) {
                    //show message that there is no more profiles left; new inline message
//                    throw new ArrayStoreException("ran out of profiles"); //temp
                    return null; //if all viewed return null
                }
            }

            this.randNumb = (int) (Math.random() * refugees.size());
            while (this.randNumb == prevNumber) {
                this.randNumb = (int) (Math.random() * refugees.size());
            }
            prevNumber = this.randNumb;

            //check if unique
            while (refugees.get(this.randNumb).isViewed()) {
                this.randNumb = (int) (Math.random() * refugees.size());
                while (this.randNumb == prevNumber) {
                    this.randNumb = (int) (Math.random() * refugees.size());
                }
                prevNumber = this.randNumb;
            }

//            setIsViewed(refugees.get(this.randNumb).getId(), Status.REFUGEE);

            return refugees.get(this.randNumb);
        }
        else return null;
    }

    public void setIsViewed(Long idInCache, Status userStatus) {
        User user = this.userCache.findBy(idInCache);
        user.setViewed(true);
        if (userStatus.equals(Status.HOST)) {
            User user2 = this.hostProfile.getHosts().get(this.randNumb);
            user2.setViewed(true);
        } else {
            User user3 = this.refugeeProfile.getRefugees().get(this.randNumb);
            user3.setViewed(true);
        }
    }

    public boolean areAllUsersViewed(Status userStatus) {
        return this.fetchRandomUniqueUser(userStatus) == null;
    }
/*
    public int calculateCurrentUserArrayIndex(CallbackQuery callbackQuery, Status userStatus) {
        if (userStatus.equals(Status.HOST)) {
            int startNavigationIndex = 0;
            for (User user : this.hostProfile.getHosts()) {
                if (Long.parseLong(callbackQuery.getId()) == user.getId()) {
                    break;
                }
                startNavigationIndex++;
            }
            return startNavigationIndex;
        } else {
            int startNavigationIndex = 0;
            for (User user : this.refugeeProfile.getRefugees()) {
                if (Long.parseLong(callbackQuery.getId()) == user.getId()) {
                    break;
                }
                startNavigationIndex++;
            }
            return startNavigationIndex;
        }
    }*/

    @Autowired
    public void setHostProfile(HostProfile hostProfile) {
        this.hostProfile = hostProfile;
    }

    @Autowired
    public void setRefugeeProfile(RefugeeProfile refugeeProfile) {
        this.refugeeProfile = refugeeProfile;
    }
}