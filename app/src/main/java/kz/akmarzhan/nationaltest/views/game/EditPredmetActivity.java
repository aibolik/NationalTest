package kz.akmarzhan.nationaltest.views.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.akmarzhan.nationaltest.R;
import kz.akmarzhan.nationaltest.adapters.UserPredmetsAdapter;
import kz.akmarzhan.nationaltest.bus.events.LoadPredmetListEvent;
import kz.akmarzhan.nationaltest.bus.events.PredmetListLoadedEvent;
import kz.akmarzhan.nationaltest.models.Predmet;
import kz.akmarzhan.nationaltest.models.UserPredmet;
import kz.akmarzhan.nationaltest.utils.Logger;
import kz.akmarzhan.nationaltest.views.BaseActivity;

/**
 * Created by Akmarzhan Raushanova on 6/4/17.
 */

public class EditPredmetActivity extends BaseActivity implements UserPredmetsAdapter.PredmetActionListener {

    @BindView(R.id.rv_predmets) RecyclerView rvPredmets;

    private UserPredmetsAdapter mPredmetsAdapter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_predmet);

        ButterKnife.bind(this);

        mPredmetsAdapter = new UserPredmetsAdapter();
        mPredmetsAdapter.setListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvPredmets.setLayoutManager(layoutManager);
        rvPredmets.setAdapter(mPredmetsAdapter);

        getUser();
    }

    @Override protected void onResume() {
        super.onResume();

        showDialog("Subjects", "Loading...");
        getBus().post(new LoadPredmetListEvent(mUser.getObjectId()));
    }

    @OnClick(R.id.fab_finish) void finishSelectingPredmets() {
        onBackPressed();
    }

    @Subscribe
    public void onPredmetListLoaded(final PredmetListLoadedEvent event) {
        hideDialog();
        mPredmetsAdapter.setPredmets(event.predmets);
    }

    @Override public void onAddPredmet(final Predmet predmet) {
        final UserPredmet userPredmet = new UserPredmet();
        userPredmet.setExp(0);
        userPredmet.setLastTestId(0);
        Logger.d("EditPredmetActivity", "onAddPredmet: " + predmet.getObjectId());
        final ArrayList<Predmet> predmetsCollection = new ArrayList<>();
        predmetsCollection.add(predmet);
        showDialog("", "Adding subject");
        Backendless.Data.of(UserPredmet.class).save(userPredmet, new AsyncCallback<UserPredmet>() {
            @Override public void handleResponse(UserPredmet loadedUserPredmet) {
                predmet.setSelected(true);
                mPredmetsAdapter.notifyDataSetChanged();
                Backendless.Data.of(UserPredmet.class).setRelation(
                        loadedUserPredmet, "predmet", predmetsCollection, new AsyncCallback<Integer>() {
                            @Override public void handleResponse(Integer response) {
                                Logger.d("EditPredmetActivity", "added predmet: " + response);
                                hideDialog();
                            }

                            @Override public void handleFault(BackendlessFault fault) {
                                hideDialog();
                            }
                        });
                BackendlessUser user = new BackendlessUser();
                user.setProperty("objectId", mUser.getObjectId());
                ArrayList<UserPredmet> userPredmets = new ArrayList<UserPredmet>();
                userPredmets.add(loadedUserPredmet);
                Backendless.Data.of(BackendlessUser.class).addRelation(
                        user, "predmets:userpredmets:n", userPredmets, new AsyncCallback<Integer>() {
                            @Override public void handleResponse(Integer response) {
                                Logger.d("EditPredmetActivity", "added relation: " + response);
                                hideDialog();
                            }

                            @Override public void handleFault(BackendlessFault fault) {
                                hideDialog();
                                showMessage(fault.getMessage());
                            }
                        });
            }

            @Override public void handleFault(BackendlessFault fault) {
                hideDialog();
                showMessage(fault.getMessage());
            }
        });
    }

    @Override public void onRemovePredmet(final Predmet predmet) {
        BackendlessUser user = new BackendlessUser();
        user.setProperty("objectId", mUser.getObjectId());
        showDialog("", "Removing subject");
        Backendless.Data.of(BackendlessUser.class).findById(mUser.getObjectId(), 1, new AsyncCallback<BackendlessUser>() {
            @Override public void handleResponse(BackendlessUser user) {
                final List<UserPredmet> userPredmets = Arrays.asList((UserPredmet[]) user.getProperty("predmets"));
                Logger.d("TestService", "userPredmetSize: " + userPredmets.size());

                for(UserPredmet userPredmet : userPredmets) {
                    if(userPredmet.getPredmet().getObjectId().equals(predmet.getObjectId())) {
                        Backendless.Data.of(UserPredmet.class).remove(userPredmet, new AsyncCallback<Long>() {
                            @Override public void handleResponse(Long response) {
                                Logger.d("EditPredmetActivity", "predmet removed: " + response);
                                predmet.setSelected(false);
                                mPredmetsAdapter.notifyDataSetChanged();
                                hideDialog();
                            }

                            @Override public void handleFault(BackendlessFault fault) {
                                showMessage(fault.getMessage());
                            }
                        });
                        break;
                    }
                }
            }

            @Override public void handleFault(BackendlessFault fault) {

            }
        });
    }
}
