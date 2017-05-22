package kz.akmarzhan.nationaltest.bus.events;

/**
 * Created by Aibol Kussain on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: aibolikdev@gmail.com
 */

public class LoadPredmetsEvent {

    public String objectId;

    public LoadPredmetsEvent(String objectId) {
        this.objectId = objectId;
    }
}
