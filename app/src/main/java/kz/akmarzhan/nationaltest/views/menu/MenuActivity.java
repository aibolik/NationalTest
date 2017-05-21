package kz.akmarzhan.nationaltest.views.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.akmarzhan.nationaltest.R;
import kz.akmarzhan.nationaltest.adapters.PredmetsAdapter;
import kz.akmarzhan.nationaltest.bus.events.LoadPredmetsEvent;
import kz.akmarzhan.nationaltest.bus.events.PredmetsLoadedEvent;
import kz.akmarzhan.nationaltest.utils.Logger;
import kz.akmarzhan.nationaltest.views.BaseActivity;

/**
 * Created by Aibol Kussain on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: aibolikdev@gmail.com
 */

public class MenuActivity extends BaseActivity {

    private static final String TAG = MenuActivity.class.getSimpleName();

    @BindView(R.id.fabtoolbar) FABToolbarLayout ftlLayout;
    @BindView(R.id.fabtoolbar_fab) FloatingActionButton fabMenu;
    @BindView(R.id.fabtoolbar_toolbar) ViewGroup fabToolbar;
    @BindView(R.id.rv_predmets) RecyclerView rvPredmets;

    private PredmetsAdapter mPredmetsAdapter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

        mPredmetsAdapter = new PredmetsAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPredmets.setLayoutManager(linearLayoutManager);
        rvPredmets.setAdapter(mPredmetsAdapter);
    }

    @Override protected void onResume() {
        super.onResume();

        getBus().post(new LoadPredmetsEvent());
    }

    @Subscribe
    public void onPredmetsLoaded(PredmetsLoadedEvent event) {
        mPredmetsAdapter.setPredmets(event.mPredmets);
    }

    @OnClick(R.id.fabtoolbar_fab) void showToolbar() {
        Logger.d(TAG, "showToolbar: ");
        ftlLayout.show();
    }

    @Override public void onBackPressed() {
        Logger.d(TAG, "onBackPressed: ");
        if(fabToolbar.getVisibility() == View.VISIBLE) {
            ftlLayout.hide();
        }
        else {
            super.onBackPressed();
        }
    }
}
