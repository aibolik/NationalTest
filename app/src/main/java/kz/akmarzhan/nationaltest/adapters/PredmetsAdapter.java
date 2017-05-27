package kz.akmarzhan.nationaltest.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.akmarzhan.nationaltest.R;
import kz.akmarzhan.nationaltest.models.UserPredmet;
import kz.akmarzhan.nationaltest.utils.Utils;

/**
 * Created by Aibol Kussain on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: aibolikdev@gmail.com
 */

public class PredmetsAdapter extends RecyclerView.Adapter<PredmetsAdapter.PredmetHolder> {

    private List<UserPredmet> mPredmets;

    public PredmetsAdapter() {
        mPredmets = new ArrayList<>();
    }

    @Override public PredmetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_predmet, parent, false);
        return new PredmetHolder(view);
    }

    @Override public void onBindViewHolder(PredmetHolder holder, int position) {
        UserPredmet predmet = mPredmets.get(position);
        Pair<Integer, Integer> levelPair = Utils.getLevelByExpereience(predmet.getExp());
        holder.tvPredmet.setText(predmet.getPredmet().getName());
        holder.tvLevel.setText(String.valueOf(levelPair.first));
        holder.tvExp.setText(predmet.getExp() + "/" + levelPair.second);
    }

    @Override public int getItemCount() {
        return mPredmets.size();
    }

    public void setPredmets(List<UserPredmet> predmets) {
        mPredmets = predmets;
        notifyDataSetChanged();
    }

    class PredmetHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_level) TextView tvLevel;
        @BindView(R.id.tv_predmet) TextView tvPredmet;
        @BindView(R.id.pb_exp) ProgressBar pbExp;
        @BindView(R.id.tv_exp) TextView tvExp;

        public PredmetHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
