package kz.akmarzhan.nationaltest.views.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.akmarzhan.nationaltest.Defaults;
import kz.akmarzhan.nationaltest.R;
import kz.akmarzhan.nationaltest.views.BaseActivity;
import kz.akmarzhan.nationaltest.views.menu.MenuActivity;

/**
 * Created by Akmarzhan Raushanova on 6/4/17.
 */

public class GameFinishActivity extends BaseActivity {

    @BindView(R.id.tv_score_count) TextView tvScoreCount;
    @BindView(R.id.tv_score_text) TextView tvScoreText;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finish);

        ButterKnife.bind(this);

        if(getIntent() != null) {
            int score = getIntent().getIntExtra(Defaults.EXTRA_SCORE, 0);
            int totalScore = getIntent().getIntExtra(Defaults.EXTRA_TOTAL_SCORE, 0);

            tvScoreCount.setText(score + "/" + totalScore);
            String scoreText;
            if(score / totalScore > 0.8) {
                scoreText = getString(R.string.game_score_excellent);
            }
            else if(score / totalScore > 0.6) {
                scoreText = getString(R.string.game_score_good);
            }
            else {
                scoreText = getString(R.string.game_score_low);
            }
            tvScoreText.setText(scoreText);
        }
    }

    @OnClick(R.id.bt_menu) void goToMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
