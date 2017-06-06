package kz.akmarzhan.nationaltest.bus.events;

/**
 * Created by Akmarzhan Raushanova on 6/4/17.
 */

public class LoadPredmetListEvent {

    public String objectId;

    public LoadPredmetListEvent(String objectId) {
        this.objectId = objectId;
    }
}
