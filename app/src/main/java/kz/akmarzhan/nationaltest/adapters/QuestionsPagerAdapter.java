package kz.akmarzhan.nationaltest.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import kz.akmarzhan.nationaltest.models.Question;
import kz.akmarzhan.nationaltest.views.game.QuestionFragment;

/**
 * Created by Akmarzhan Raushanova on 5/27/17.
 */

public class QuestionsPagerAdapter extends FragmentPagerAdapter {

    private List<Question> questions;

    public QuestionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override public Fragment getItem(int position) {
        return QuestionFragment.newInstance(questions.get(position));
    }

    @Override public int getCount() {
        return questions.size();
    }
}
