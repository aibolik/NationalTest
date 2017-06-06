package kz.akmarzhan.nationaltest.views.game;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.akmarzhan.nationaltest.Defaults;
import kz.akmarzhan.nationaltest.R;
import kz.akmarzhan.nationaltest.adapters.QuestionsPagerAdapter;
import kz.akmarzhan.nationaltest.bus.events.FinishTestEvent;
import kz.akmarzhan.nationaltest.models.Question;
import kz.akmarzhan.nationaltest.models.Test;
import kz.akmarzhan.nationaltest.models.UserAnswer;
import kz.akmarzhan.nationaltest.views.BaseActivity;

/**
 * Created by Akmarzhan Raushanova on 5/27/17.
 */

public class GameActivity
        extends BaseActivity
        implements QuestionFragment.QuestionActionsListener {

    private static final String TAG = GameActivity.class.getSimpleName();

    @BindView(R.id.pager) ViewPager pager;
    @BindView(R.id.tv_questions_progress) TextView tvQuestionsProgress;
    @BindView(R.id.pb_questions_progress) ProgressBar pbQuestionsProgress;
    @BindView(R.id.tv_predmet_name) TextView tvPredmetName;
    @BindView(R.id.iv_next) ImageView ivNextQuestion;
    @BindView(R.id.iv_prev) ImageView ivPrevQuestion;

    private QuestionsPagerAdapter pagerAdapter;

    private String predmetId;
    private Test test;
    private String predmetName;
    private int currentQuestion;

    private UserAnswer[] answers;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);

        getUser();

        currentQuestion = 0;
        predmetId = getIntent().getStringExtra(Defaults.EXTRA_PREDMET_ID);
        test = getIntent().getParcelableExtra(Defaults.EXTRA_TEST);
        predmetName = getIntent().getStringExtra(Defaults.EXTRA_PREDMET_NAME);

        answers = new UserAnswer[test.getQuestions().size()];

        for(int i = 0; i < answers.length; i++) {
            answers[i] = new UserAnswer();
        }

        tvPredmetName.setText(predmetName);
        pbQuestionsProgress.setMax(test.getQuestions().size());

        invalidateControllers();

        pagerAdapter = new QuestionsPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setQuestions(test.getQuestions());
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override public void onPageSelected(int position) {
                currentQuestion = position;
                invalidateControllers();
            }

            @Override public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.iv_next, R.id.iv_prev}) void nextQuestion(View view) {
        if(view.getId() == R.id.iv_next) {
            currentQuestion++;
        }
        else {
            currentQuestion--;
        }
        pager.setCurrentItem(currentQuestion);
    }

    private void invalidateControllers() {
        if(currentQuestion == 0) {
            ivPrevQuestion.setVisibility(View.GONE);
        }
        else {
            ivPrevQuestion.setVisibility(View.VISIBLE);
        }
        if(currentQuestion == test.getQuestions().size() - 1) {
            ivNextQuestion.setVisibility(View.GONE);
        }
        else {
            ivNextQuestion.setVisibility(View.VISIBLE);
        }
        tvQuestionsProgress.setText((currentQuestion + 1) + "/" + test.getQuestions().size());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pbQuestionsProgress.setProgress(currentQuestion + 1, true);
        }
        else {
            pbQuestionsProgress.setProgress(currentQuestion + 1);
        }
    }


    @Override public void onSelectSingleAnswer(char answer) {
        answers[currentQuestion].singleAnswer = answer;
    }

    @Override public void onSelectMultipleAnswer(String answer, boolean checked) {
        answers[currentQuestion].multipleAnswer.put(answer, checked);
    }

    @Override public void onFinish() {
        int score = 0;
        int totalScore = 0;
        List<Question> questions = test.getQuestions();
        for(int i = 0; i < answers.length; i++) {
            Question q = questions.get(i);
            if(q.getType() == Question.TYPE_SINGLE) {
                totalScore += 1;
                score = score + (q.getAnswers().equals(String.valueOf(answers[i].singleAnswer)) ? 1 : 0);
            }
            else {
                String[] ans = q.getAnswers().split(",");
                totalScore += ans.length;
                if(answers[i].multipleAnswer.get("a")) {
                    score = score + (q.getAnswers().contains("a") ? 1 : -1);
                }
                if(answers[i].multipleAnswer.get("b")) {
                    score = score + (q.getAnswers().contains("b") ? 1 : -1);
                }
                if(answers[i].multipleAnswer.get("c")) {
                    score = score + (q.getAnswers().contains("c") ? 1 : -1);
                }
                if(answers[i].multipleAnswer.get("d")) {
                    score = score + (q.getAnswers().contains("d") ? 1 : -1);
                }
                if(answers[i].multipleAnswer.get("e")) {
                    score = score + (q.getAnswers().contains("e") ? 1 : -1);
                }
            }
        }
        getBus().post(new FinishTestEvent(mUser.getObjectId(), predmetId, score, test.getId()));
        Intent intent = new Intent(this, GameFinishActivity.class);
        intent.putExtra(Defaults.EXTRA_TOTAL_SCORE, totalScore);
        intent.putExtra(Defaults.EXTRA_SCORE, score);
        startActivity(intent);
    }
}
