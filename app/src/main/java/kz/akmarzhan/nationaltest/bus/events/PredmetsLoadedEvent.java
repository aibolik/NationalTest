package kz.akmarzhan.nationaltest.bus.events;

import java.util.List;

import kz.akmarzhan.nationaltest.models.Predmet;

/**
 * Created by Aibol Kussain on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: aibolikdev@gmail.com
 */

public class PredmetsLoadedEvent {

    public List<Predmet> mPredmets;

    public PredmetsLoadedEvent(List<Predmet> predmets) {
        mPredmets = predmets;
    }
}
