package kz.akmarzhan.nationaltest.restapi;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import kz.akmarzhan.nationaltest.bus.events.FinishTestEvent;
import kz.akmarzhan.nationaltest.bus.events.LoadPredmetListEvent;
import kz.akmarzhan.nationaltest.bus.events.LoadPredmetsEvent;
import kz.akmarzhan.nationaltest.bus.events.LoadTestEvent;
import kz.akmarzhan.nationaltest.bus.events.PredmetListLoadedEvent;
import kz.akmarzhan.nationaltest.bus.events.TestLoadedEvent;
import kz.akmarzhan.nationaltest.bus.events.UserPredmetsLoadedEvent;
import kz.akmarzhan.nationaltest.models.Predmet;
import kz.akmarzhan.nationaltest.models.Test;
import kz.akmarzhan.nationaltest.models.UserPredmet;
import kz.akmarzhan.nationaltest.utils.Logger;

/**
 * Created by Akmarzhan Raushanova on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: akmarzhan.raushnanova@is.sdu.edu.kz
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
                UserPredmet[] userPredmetsArray = (UserPredmet[]) user.getProperty("predmets");
                List<UserPredmet> userPredmets = Arrays.asList(userPredmetsArray);
                int userExp = 0;

                for (int i = 0; i < userPredmets.size(); i++) {
                    userExp += userExp += userPredmets.get(i).getExp();
                }

//                for (Map.Entry<String, Object> entry : props.entrySet()) {
//                    if(entry.getKey().equals("predmets")) {
//                        for(HashMap<String, Object> predmetProps : (HashMap<String, Object>[]) entry.getValue()) {
//                            UserPredmet userPredmet = UserPredmet.createFromMap(predmetProps);
//                            userExp += userPredmet.getExp();
//                            userPredmets.add(userPredmet);
//                        }
//                    }
//                }

                mBus.post(new UserPredmetsLoadedEvent(userPredmets, userExp));
            }

            @Override public void handleFault(BackendlessFault fault) {

            }
        });
    }

    @Subscribe
    public void onGetTests(final LoadTestEvent event) {
        Logger.d(TAG, "Getting tests: ");
        Backendless.Data.of(Predmet.class).findById(event.predmetObjectId, 1, new AsyncCallback<Predmet>() {
            @Override public void handleResponse(Predmet predmet) {
                final String predmetName = predmet.getName();
                Test test = new Test();
                for (Test t : predmet.getTests()) {
                    if (t.getId() > event.lastTestId) {
                        test = t;
                        break;
                    }
                }
                Logger.d(TAG, "Test: " + test.getId());
                Logger.d(TAG, "Last test ID: " + event.lastTestId);
                if (test.getId() == 0) {
                    mBus.post(new TestLoadedEvent(predmetName, test));
                    return;
                }
                Backendless.Data.of(Test.class).findById(test, 1, new AsyncCallback<Test>() {
                    @Override public void handleResponse(Test test) {
                        mBus.post(new TestLoadedEvent(predmetName, test));
                    }

                    @Override public void handleFault(BackendlessFault fault) {

                    }
                });
            }

            @Override public void handleFault(BackendlessFault fault) {

            }
        });
    }

    @Subscribe
    public void finishTest(final FinishTestEvent event) {
        Backendless.Data.of(BackendlessUser.class).findById(event.userId, 1, new AsyncCallback<BackendlessUser>() {
            @Override public void handleResponse(final BackendlessUser user) {
                List<UserPredmet> userPredmets = Arrays.asList((UserPredmet[]) user.getProperty("predmets"));
                Logger.d("TestService", "userPredmetSize: " + userPredmets.size());
                for (UserPredmet predmet : userPredmets) {
                    if (predmet.getPredmet().getObjectId().equals(event.predmetObjectId)) {
                        predmet.setExp(predmet.getExp() + event.score);
                        predmet.setLastTestId(event.testId);
                        Backendless.Data.save(predmet, new AsyncCallback<UserPredmet>() {
                            @Override public void handleResponse(UserPredmet userPredmet) {
                                Logger.d("TestService", "handleResponse: " + userPredmet);
                            }

                            @Override public void handleFault(BackendlessFault fault) {

                            }
                        });
                        break;
                    }
                }
                user.setProperty("exp", (Integer)user.getProperty("exp") + event.score);
                Backendless.UserService.update(user, new AsyncCallback<BackendlessUser>() {
                    @Override public void handleResponse(BackendlessUser user) {
                        Logger.d("TestService", "user updated score: " + user);
                    }

                    @Override public void handleFault(BackendlessFault fault) {

                    }
                });
            }

            @Override public void handleFault(BackendlessFault fault) {

            }
        });
    }

    @Subscribe
    public void loadListOfPredmets(final LoadPredmetListEvent event) {
        Backendless.Data.of(BackendlessUser.class).findById(event.objectId, 1, new AsyncCallback<BackendlessUser>() {
            @Override public void handleResponse(BackendlessUser user) {
                final List<UserPredmet> userPredmets = Arrays.asList((UserPredmet[]) user.getProperty("predmets"));
                Logger.d("TestService", "userPredmetSize: " + userPredmets.size());

                Backendless.Data.of(Predmet.class).find(new AsyncCallback<List<Predmet>>() {
                    @Override public void handleResponse(List<Predmet> predmets) {

                        for (Predmet predmet : predmets) {
                            for(UserPredmet userPredmet : userPredmets) {
                                if(userPredmet.getPredmet().getObjectId().equals(predmet.getObjectId())) {
                                    predmet.setSelected(true);
                                    break;
                                }
                            }
                        }
                        mBus.post(new PredmetListLoadedEvent(predmets));

                    }

                    @Override public void handleFault(BackendlessFault fault) {

                    }
                });
            }

            @Override public void handleFault(BackendlessFault fault) {

            }
        });
    }

}
