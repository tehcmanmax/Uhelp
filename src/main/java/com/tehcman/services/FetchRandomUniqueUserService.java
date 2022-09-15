package com.tehcman.services;

import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.printers.HostProfile;
import com.tehcman.printers.RefugeeProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public class FetchRandomUniqueUserService {
    private final UserCache userCache;
    private final HostProfile hostProfile;
    private final RefugeeProfile refugeeProfile;
    private int prevNumber = -1;
    private int randNumb;

    @Autowired
    public FetchRandomUniqueUserService(UserCache userCache, HostProfile hostProfile, RefugeeProfile refugeeProfile) {
        this.userCache = userCache;
        this.hostProfile = hostProfile;
        this.refugeeProfile = refugeeProfile;
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

            int randNumb = (int) (Math.random() * this.hostProfile.getHosts().size());
            while (randNumb == prevNumber) {
                randNumb = (int) (Math.random() * this.hostProfile.getHosts().size());
            }
            prevNumber = randNumb;

            //check if unique
            while (this.hostProfile.getHosts().get(randNumb).isViewed()) {
                randNumb = (int) (Math.random() * this.hostProfile.getHosts().size());
                while (randNumb == prevNumber) {
                    randNumb = (int) (Math.random() * this.hostProfile.getHosts().size());
                }
                prevNumber = randNumb;
            }

            setIsViewed(this.hostProfile.getHosts().get(randNumb).getId(), Status.HOST);

            return this.hostProfile.getHosts().get(randNumb);
        } else {
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

            int randNumb = (int) (Math.random() * refugees.size());
            while (randNumb == prevNumber) {
                randNumb = (int) (Math.random() * refugees.size());
            }
            prevNumber = randNumb;

            //check if unique
            while (refugees.get(randNumb).isViewed()) {
                randNumb = (int) (Math.random() * refugees.size());
                while (randNumb == prevNumber) {
                    randNumb = (int) (Math.random() * refugees.size());
                }
                prevNumber = randNumb;
            }

            setIsViewed(refugees.get(randNumb).getId(), Status.REFUGEE);

            return refugees.get(randNumb);
        }
    }

    private void setIsViewed(Long idInCache, Status userStatus) {
        this.userCache.findBy(idInCache).setViewed(true);
        if (userStatus.equals(Status.HOST))
            this.hostProfile.getHosts().get(randNumb).setViewed(true);
        else this.refugeeProfile.getRefugees().get(randNumb).setViewed(true);
    }

    public boolean areAllUsersViewed(Status userStatus) {
        return this.fetchRandomUniqueUser(userStatus) == null;
    }

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
    }
}