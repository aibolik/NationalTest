package kz.akmarzhan.nationaltest.bus.events;

import java.util.List;

import kz.akmarzhan.nationaltest.models.User;

/**
 * Created by aibol on 6/6/17.
 */

public class ListOfUsersLoadedEvent {

    public List<User> users;
    public int rating;

    public ListOfUsersLoadedEvent(List<User> users, int rating) {
        this.users = users;
        this.rating = rating;
    }
}
