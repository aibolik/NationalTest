package kz.akmarzhan.nationaltest.bus;

import com.squareup.otto.Bus;

/**
 * Created by Akmarzhan Raushanova on 5/14/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: akmarzhan.raushnanova@is.sdu.edu.kz
 */

public class BusProvider {

    private static final Bus mBus = new Bus();

    public static Bus getInstance() {
        return mBus;
    }

    private BusProvider() {

    }

}
