package kz.akmarzhan.nationaltest.views.game;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import kz.akmarzhan.nationaltest.Defaults;
import kz.akmarzhan.nationaltest.R;
import kz.akmarzhan.nationaltest.models.Question;
import kz.akmarzhan.nationaltest.utils.Logger;

/**
 * Created by aibol on 5/27/17.
 */

public class QuestionFragment extends Fragment {

    @BindView(R.id.tv_text) TextView tvText;

    @Nullable @BindView(R.id.rb_a) RadioButton rbA;
    @Nullable @BindView(R.id.rb_b) RadioButton rbB;
    @Nullable @BindView(R.id.rb_c) RadioButton rbC;
    @Nullable @BindView(R.id.rb_d) RadioButton rbD;
    @Nullable @BindView(R.id.rb_e) RadioButton rbE;

    @Nullable @BindView(R.id.cb_a) CheckBox cbA;
    @Nullable @BindView(R.id.cb_b) CheckBox cbB;
    @Nullable @BindView(R.id.cb_c) CheckBox cbC;
    @Nullable @BindView(R.id.cb_d) CheckBox cbD;
    @Nullable @BindView(R.id.cb_e) CheckBox cbE;


    private JSONObject variants;
    private Question question;

    private QuestionActionsListener listener;

    public static QuestionFragment newInstance(Question question) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable(Defaults.ARG_QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }

    private void readBundle(Bundle args) {
        if(args != null) {
            question = args.getParcelable(Defaults.ARG_QUESTION);
        }
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof QuestionActionsListener) {
            listener = (QuestionActionsListener) context;
        }
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readBundle(getArguments());
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if(question.getType() == Question.TYPE_SINGLE) {
            view = inflater.inflate(R.layout.fragment_single_choice_question, container, false);
        }
        else {
            view = inflater.inflate(R.layout.fragment_multiple_choice_question, container, false);
        }
        ButterKnife.bind(this, view);

        Logger.d("QuestionFragment", "variants: " + question.getVariants());
        Logger.d("QuestionFragment", "answers: " + question.getAnswers());

        try {
            variants = new JSONObject(question.getVariants());

            if(question.getType() == Question.TYPE_SINGLE) {
                rbA.setText(variants.getString("a"));
                rbB.setText(variants.getString("b"));
                rbC.setText(variants.getString("c"));
                rbD.setText(variants.getString("d"));
                rbE.setText(variants.getString("e"));
            }

            else {
                cbA.setText(variants.getString("a"));
                cbB.setText(variants.getString("b"));
                cbC.setText(variants.getString("c"));
                cbD.setText(variants.getString("d"));
                cbE.setText(variants.getString("e"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        tvText.setText(question.getText());

        return view;
    }

    @OnClick(R.id.bt_finish) void finishTest() {
        if(listener != null) {
            listener.onFinish();
        }
    }

    @Optional
    @OnClick({R.id.rb_a, R.id.rb_b, R.id.rb_c, R.id.rb_d, R.id.rb_e}) void onSelectSingleAnswer(RadioButton view) {
        boolean checked = view.isChecked();
        switch (view.getId()) {
            case R.id.rb_a:
                if(checked) {
                    listener.onSelectSingleAnswer('a');
                }
                break;
            case R.id.rb_b:
                if(checked) {
                    listener.onSelectSingleAnswer('b');
                }
                break;
            case R.id.rb_c:
                if(checked) {
                    listener.onSelectSingleAnswer('c');
                }
                break;
            case R.id.rb_d:
                if(checked) {
                    listener.onSelectSingleAnswer('d');
                }
                break;
            case R.id.rb_e:
                if(checked) {
                    listener.onSelectSingleAnswer('e');
                }
                break;
        }
    }

    @Optional
    @OnClick({R.id.cb_a, R.id.cb_b, R.id.cb_c, R.id.cb_d, R.id.cb_e}) void onSelectMultipleAnswer(CheckBox view) {
        boolean checked = view.isChecked();
        switch (view.getId()) {
            case R.id.cb_a:
                listener.onSelectMultipleAnswer("a", checked);
                break;
            case R.id.cb_b:
                listener.onSelectMultipleAnswer("b", checked);
                break;
            case R.id.cb_c:
                listener.onSelectMultipleAnswer("c", checked);
                break;
            case R.id.cb_d:
                listener.onSelectMultipleAnswer("d", checked);
                break;
            case R.id.cb_e:
                listener.onSelectMultipleAnswer("e", checked);
                break;
        }
    }

    public interface QuestionActionsListener {

        void onSelectMultipleAnswer(String answer, boolean checked);
        void onSelectSingleAnswer(char answer);
        void onFinish();

    }


}
