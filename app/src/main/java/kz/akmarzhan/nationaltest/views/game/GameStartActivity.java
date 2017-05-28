package kz.akmarzhan.nationaltest.views.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.akmarzhan.nationaltest.Defaults;
import kz.akmarzhan.nationaltest.R;
import kz.akmarzhan.nationaltest.bus.events.LoadTestEvent;
import kz.akmarzhan.nationaltest.bus.events.TestLoadedEvent;
import kz.akmarzhan.nationaltest.models.Test;
import kz.akmarzhan.nationaltest.views.BaseActivity;

/**
 * Created by aibol on 5/27/17.
 */

public class GameStartActivity extends BaseActivity {

    private static final String TAG = GameStartActivity.class.getSimpleName();

    @BindView(R.id.tv_questions_count) TextView tvQuestionsCount;

    private String predmetId;
    private int lastTestId;

    private String predmetName;
    private Test test;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        ButterKnife.bind(this);

        predmetId = getIntent().getStringExtra(Defaults.EXTRA_PREDMET_ID);
        lastTestId = getIntent().getIntExtra(Defaults.EXTRA_LAST_TEST_ID, 0);

    }

    @Override protected void onResume() {
        super.onResume();

        getBus().post(new LoadTestEvent(predmetId, lastTestId));
    }

    @Subscribe
    public void onTestLoaded(TestLoadedEvent event) {
        this.test = event.mTest;
        this.predmetName = event.mPredmetName;
        tvQuestionsCount.setText(String.valueOf(test.getQuestions().size()));
    }

    @OnClick(R.id.bt_start) void startTest() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(Defaults.EXTRA_TEST, test);
        intent.putExtra(Defaults.EXTRA_PREDMET_NAME, predmetName);
        intent.putExtra(Defaults.EXTRA_PREDMET_ID, predmetId);
        startActivity(intent);
    }

}
