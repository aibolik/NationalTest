package kz.akmarzhan.nationaltest.restapi;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import kz.akmarzhan.nationaltest.bus.events.LoadPredmetsEvent;
import kz.akmarzhan.nationaltest.bus.events.PredmetsLoadedEvent;
import kz.akmarzhan.nationaltest.models.Predmet;
import kz.akmarzhan.nationaltest.utils.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        mApi.getPredmets().enqueue(new Callback<List<Predmet>>() {
            @Override public void onResponse(Call<List<Predmet>> call, Response<List<Predmet>> response) {
                Logger.d(TAG, "onResponse: " + call.request().toString());
                mBus.post(new PredmetsLoadedEvent(response.body()));
            }

            @Override public void onFailure(Call<List<Predmet>> call, Throwable t) {

            }
        });
    }

}
