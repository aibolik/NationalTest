package kz.akmarzhan.nationaltest.restapi;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Map;

import kz.akmarzhan.nationaltest.bus.events.LoadPredmetsEvent;

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
    public void onGetPredmets(LoadPredmetsEvent event) {
        Backendless.Persistence.of( "Users" ).findById(event.objectId, new AsyncCallback<Map>() {
            @Override public void handleResponse(Map user) {

            }

            @Override public void handleFault(BackendlessFault fault) {

            }
        });

//        mApi.getPredmets().enqueue(new Callback<List<Predmet>>() {
//            @Override public void onResponse(Call<List<Predmet>> call, Response<List<Predmet>> response) {
//                Logger.d(TAG, "onResponse: " + call.request().toString());
//                mBus.post(new PredmetsLoadedEvent(response.body()));
//            }
//
//            @Override public void onFailure(Call<List<Predmet>> call, Throwable t) {
//
//            }
//        });
    }

}
