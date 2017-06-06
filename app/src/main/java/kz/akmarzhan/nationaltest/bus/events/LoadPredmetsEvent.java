package kz.akmarzhan.nationaltest.bus.events;

/**
 * Created by Akmarzhan Raushanova on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: akmarzhan.raushnanova@is.sdu.edu.kz
 */

public class LoadPredmetsEvent {

    public String objectId;

    public LoadPredmetsEvent(String objectId) {
        this.objectId = objectId;
    }
}
