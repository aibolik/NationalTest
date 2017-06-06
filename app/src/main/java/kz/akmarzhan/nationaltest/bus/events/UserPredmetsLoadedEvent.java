package kz.akmarzhan.nationaltest.bus.events;

import java.util.List;

import kz.akmarzhan.nationaltest.models.UserPredmet;

/**
 * Created by Akmarzhan Raushanova on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: akmarzhan.raushnanova@is.sdu.edu.kz
 */

public class UserPredmetsLoadedEvent {

    public List<UserPredmet> mPredmets;
    public int mUserExp;

    public UserPredmetsLoadedEvent(List<UserPredmet> predmets, int userExp) {
        mPredmets = predmets;
        mUserExp = userExp;
    }
}
