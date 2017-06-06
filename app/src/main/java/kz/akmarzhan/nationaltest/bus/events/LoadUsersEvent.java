package kz.akmarzhan.nationaltest.bus.events;

/**
 * Created by aibol on 6/6/17.
 */

public class LoadUsersEvent {

    public String userId;

    public LoadUsersEvent(String userId) {
        this.userId = userId;
    }
}
