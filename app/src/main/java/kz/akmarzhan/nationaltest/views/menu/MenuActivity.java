package kz.akmarzhan.nationaltest.views.menu;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import kz.akmarzhan.nationaltest.Defaults;
import kz.akmarzhan.nationaltest.R;
import kz.akmarzhan.nationaltest.adapters.PredmetsAdapter;
import kz.akmarzhan.nationaltest.bus.events.LoadPredmetsEvent;
import kz.akmarzhan.nationaltest.bus.events.UserPredmetsLoadedEvent;
import kz.akmarzhan.nationaltest.models.FibonacciLevel;
import kz.akmarzhan.nationaltest.models.UserPredmet;
import kz.akmarzhan.nationaltest.utils.Utils;
import kz.akmarzhan.nationaltest.views.BaseActivity;
import kz.akmarzhan.nationaltest.views.game.EditPredmetActivity;
import kz.akmarzhan.nationaltest.views.game.GameStartActivity;
import kz.akmarzhan.nationaltest.views.instructions.InstructionsActivity;
import kz.akmarzhan.nationaltest.views.rating.RatingActivity;
import kz.akmarzhan.nationaltest.views.settings.SettingsActivity;

/**
 * Created by Akmarzhan Raushanova on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: akmarzhan.raushnanova@is.sdu.edu.kz
 */

public class MenuActivity extends BaseActivity implements PredmetsAdapter.PredmetClickListener {

    private static final String TAG = MenuActivity.class.getSimpleName();

    @BindView(R.id.fabtoolbar) FABToolbarLayout ftlLayout;
    @BindView(R.id.fabtoolbar_fab) FloatingActionButton fabMenu;
    @BindView(R.id.fabtoolbar_toolbar) ViewGroup fabToolbar;
    @BindView(R.id.rv_predmets) RecyclerView rvPredmets;
    @BindView(R.id.tv_name) TextView tvUserName;
    @BindView(R.id.tv_level_info) TextView tvUserLevelInfo;
    @BindView(R.id.tv_level) TextView tvUserLevel;

    private PredmetsAdapter mPredmetsAdapter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

        mPredmetsAdapter = new PredmetsAdapter();
        mPredmetsAdapter.setListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPredmets.setLayoutManager(linearLayoutManager);
        rvPredmets.setAdapter(mPredmetsAdapter);

        getUser();

        tvUserName.setText(mUser.getName());
    }

    @Override protected void onResume() {
        super.onResume();

        showDialog("", "Loading...");
        getBus().post(new LoadPredmetsEvent(mUser.getObjectId()));
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Subscribe
    public void onPredmetsLoaded(final UserPredmetsLoadedEvent event) {
        hideDialog();
        mPredmetsAdapter.setPredmets(event.mPredmets);
        realm.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                mUser.setExp(event.mUserExp);
            }
        });
        FibonacciLevel level = Utils.getLevelByExpereience(mUser.getExp());
        tvUserLevel.setText(String.valueOf(level.level));
        Resources res = getResources();
        String userLevelInfo = res.getString(R.string.user_level_info, mUser.getExp(), level.level);
        tvUserLevelInfo.setText(userLevelInfo);
    }

    @OnClick(R.id.fabtoolbar_fab) void showToolbar() {
        ftlLayout.show();
    }

    @OnClick(R.id.iv_edit) void openPredmetEdit() {
        Intent intent = new Intent(this, EditPredmetActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.iv_instructions) void openInstructions() {
        Intent intent = new Intent(this, InstructionsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.iv_rating) void openRating() {
        Intent intent = new Intent(this, RatingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.iv_settings) void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override public void onPredmetClick(UserPredmet userPredmet) {
        Intent intent = new Intent(this, GameStartActivity.class);
        intent.putExtra(Defaults.EXTRA_PREDMET_ID, userPredmet.getPredmet().getObjectId());
        intent.putExtra(Defaults.EXTRA_LAST_TEST_ID, userPredmet.getLastTestId());

        startActivity(intent);
    }

    @Override public void onBackPressed() {
        if (fabToolbar.getVisibility() == View.VISIBLE) {
            ftlLayout.hide();
        } else {
            super.onBackPressed();
        }
    }
}
