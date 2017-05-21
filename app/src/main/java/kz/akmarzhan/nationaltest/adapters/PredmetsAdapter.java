package kz.akmarzhan.nationaltest.adapters;

import android.support.v7.widget.RecyclerView;
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
import kz.akmarzhan.nationaltest.models.Predmet;

/**
 * Created by Aibol Kussain on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: aibolikdev@gmail.com
 */

public class PredmetsAdapter extends RecyclerView.Adapter<PredmetsAdapter.PredmetHolder> {

    private List<Predmet> mPredmets;

    public PredmetsAdapter() {
        mPredmets = new ArrayList<>();
    }

    @Override public PredmetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_predmet, parent, false);
        return new PredmetHolder(view);
    }

    @Override public void onBindViewHolder(PredmetHolder holder, int position) {
        Predmet predmet = mPredmets.get(position);
        holder.tvPredmet.setText(predmet.getName());
    }

    @Override public int getItemCount() {
        return mPredmets.size();
    }

    public void setPredmets(List<Predmet> predmets) {
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
