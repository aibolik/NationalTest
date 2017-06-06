package kz.akmarzhan.nationaltest.bus.events;

/**
 * Created by Akmarzhan Raushanova on 5/27/17.
 */

public class LoadTestEvent {

    public String predmetObjectId;
    public int lastTestId;

    public LoadTestEvent(String predmetObjectId, int lastTestId) {
        this.predmetObjectId = predmetObjectId;
        this.lastTestId = lastTestId;
    }
}
