package kz.akmarzhan.nationaltest.views.rating;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.akmarzhan.nationaltest.R;
import kz.akmarzhan.nationaltest.adapters.UsersAdapter;
import kz.akmarzhan.nationaltest.bus.events.ListOfUsersLoadedEvent;
import kz.akmarzhan.nationaltest.bus.events.LoadUsersEvent;
import kz.akmarzhan.nationaltest.utils.Utils;
import kz.akmarzhan.nationaltest.views.BaseActivity;

/**
 * Created by aibol on 6/6/17.
 */

public class RatingActivity extends BaseActivity {

    @BindView(R.id.tv_user_level) TextView tvUserLevel;
    @BindView(R.id.tv_user_name) TextView tvUserName;
    @BindView(R.id.tv_user_exp) TextView tvUserExp;
    @BindView(R.id.rv_users) RecyclerView rvUsers;
    @BindView(R.id.tv_rating) TextView tvRating;

    private UsersAdapter mAdapter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ButterKnife.bind(this);

        mAdapter = new UsersAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvUsers.setLayoutManager(layoutManager);
        rvUsers.setAdapter(mAdapter);

        getUser();

        int level = Utils.getLevelByExpereience(mUser.getExp()).level;

        tvUserLevel.setText(String.valueOf(level));
        tvUserName.setText(String.valueOf(mUser.getName()));
        tvUserExp.setText(getString(R.string.user_level_info, mUser.getExp(), level));
    }

    @Override protected void onResume() {
        super.onResume();

        getBus().post(new LoadUsersEvent(mUser.getObjectId()));
    }

    @Subscribe
    public void onListOfUsersLoaded(ListOfUsersLoadedEvent event) {
        tvRating.setText(getString(R.string.rating_user_rating, event.rating, event.users.size()));
        mAdapter.setCurrentUserId(mUser.getObjectId());
        mAdapter.setUsers(event.users);
    }

}
