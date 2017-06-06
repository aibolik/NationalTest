package kz.akmarzhan.nationaltest.restapi;

import java.util.List;

import kz.akmarzhan.nationaltest.models.Predmet;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Akmarzhan Raushanova on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: akmarzhan.raushnanova@is.sdu.edu.kz
 */

public interface TestApi {

    String BASE_URL = "https://api.backendless.com/8F478107-785F-D829-FFDF-67D925063F00/9FA46096-E183-6ADA-FF28-1D0F7F083D00/data/";

    /*
        Predmets request APIs
    */

    @GET("predmets")
    Call<List<Predmet>> getPredmets();

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
