package kz.akmarzhan.nationaltest;

import com.backendless.Backendless;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import kz.akmarzhan.nationaltest.bus.BusProvider;
import kz.akmarzhan.nationaltest.models.Predmet;
import kz.akmarzhan.nationaltest.models.Test;
import kz.akmarzhan.nationaltest.models.UserPredmet;
import kz.akmarzhan.nationaltest.restapi.TestApi;
import kz.akmarzhan.nationaltest.restapi.TestService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aibol Kussain on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: aibolikdev@gmail.com
 */

public class Application extends android.app.Application {

    public static final boolean DEBUG = true;

    private TestService mTestService;
    private Bus mBus = BusProvider.getInstance();

    @Override public void onCreate() {
        super.onCreate();

        Backendless.initApp( getApplicationContext(),
                Defaults.BACKENDLESS_APPLICATION_ID,
                Defaults.BACKENDLESS_API_KEY);
        Backendless.Data.mapTableToClass("predmets", Predmet.class);
        Backendless.Data.mapTableToClass("tests", Test.class);
        Backendless.Data.mapTableToClass("userpredmets", UserPredmet.class);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        mTestService = new TestService(buildApi(), mBus);

        mBus.register(mTestService);
        mBus.register(this);

    }

    private TestApi buildApi() {
        Gson gson = new GsonBuilder()
                .create();

        return new Retrofit.Builder()
                .baseUrl(TestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(TestApi.class);
    }


//    private OkHttpClient.Builder createBuilder() {
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Interceptor.Chain chain) throws IOException, java.io.IOException {
//                Request original = chain.request();
//
//                Request request = original.newBuilder()
//                        .header("application-id", BuildConfig.BACKENDLESS_APP_ID)
//                        .header("secret-key", BuildConfig.BACKENDLESS_SECRET_KEY)
//                        .method(original.method(), original.body())
//                        .build();
//
//                return chain.proceed(request);
//            }
//        });
//
//        return httpClient;
//    }

}
