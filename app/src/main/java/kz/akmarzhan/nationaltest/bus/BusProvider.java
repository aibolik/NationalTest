package kz.akmarzhan.nationaltest.bus;

import com.squareup.otto.Bus;

/**
 * Created by Aibol Kussain on 5/14/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: aibolikdev@gmail.com
 */

public class BusProvider {

    private static final Bus mBus = new Bus();

    public static Bus getInstance() {
        return mBus;
    }

    private BusProvider() {

    }

}
