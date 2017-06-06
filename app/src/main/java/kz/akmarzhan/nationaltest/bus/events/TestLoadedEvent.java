package kz.akmarzhan.nationaltest.bus.events;

import kz.akmarzhan.nationaltest.models.Test;

/**
 * Created by Akmarzhan Raushanova on 5/27/17.
 */

public class TestLoadedEvent {

    public String mPredmetName;
    public Test mTest;

    public TestLoadedEvent(String predmetName, Test mTest) {
        mPredmetName = predmetName;
        this.mTest = mTest;
    }
}
