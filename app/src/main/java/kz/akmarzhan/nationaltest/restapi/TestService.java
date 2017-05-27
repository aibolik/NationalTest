package kz.akmarzhan.nationaltest.restapi;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kz.akmarzhan.nationaltest.bus.events.LoadPredmetsEvent;
import kz.akmarzhan.nationaltest.bus.events.UserPredmetsLoadedEvent;
import kz.akmarzhan.nationaltest.models.UserPredmet;

/**
 * Created by Aibol Kussain on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: aibolikdev@gmail.com
 */

public class TestService {

    private static final String TAG = TestService.class.getSimpleName();

    private TestApi mApi;
    private Bus mBus;

    public TestService(TestApi api, Bus bus) {
        mApi = api;
        mBus = bus;
    }

    @Subscribe
    public void onGetPredmets(final LoadPredmetsEvent event) {
        Backendless.Data.of(BackendlessUser.class).findById(event.objectId, 2, new AsyncCallback<BackendlessUser>() {
            @Override public void handleResponse(BackendlessUser user) {
                Map<String, Object> props = user.getProperties();
                ArrayList<UserPredmet> userPredmets = new ArrayList<UserPredmet>();
                int userExp = 0;
                for (Map.Entry<String, Object> entry : props.entrySet()) {
                    System.out.println(entry.getKey() + "/" + entry.getValue());
                    if(entry.getKey().equals("predmets")) {
                        for(HashMap<String, Object> predmetProps : (HashMap<String, Object>[]) entry.getValue()) {
                            UserPredmet userPredmet = UserPredmet.createFromMap(predmetProps);
                            userExp += userPredmet.getExp();
                            userPredmets.add(userPredmet);
                        }
                    }
                }
                mBus.post(new UserPredmetsLoadedEvent(userPredmets, userExp));
            }

            @Override public void handleFault(BackendlessFault fault) {

            }
        });

//        mApi.getPredmets().enqueue(new Callback<List<Predmet>>() {
//            @Override public void onResponse(Call<List<Predmet>> call, Response<List<Predmet>> response) {
//                Logger.d(TAG, "onResponse: " + call.request().toString());
//                mBus.post(new UserPredmetsLoadedEvent(response.body()));
//            }
//
//            @Override public void onFailure(Call<List<Predmet>> call, Throwable t) {
//
//            }
//        });
    }

}
