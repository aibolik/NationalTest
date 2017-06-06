package kz.akmarzhan.nationaltest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.akmarzhan.nationaltest.R;
import kz.akmarzhan.nationaltest.models.Predmet;

/**
 * Created by Akmarzhan Raushanova on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: akmarzhan.raushnanova@is.sdu.edu.kz
 */

public class UserPredmetsAdapter extends RecyclerView.Adapter<UserPredmetsAdapter.UserPredmetHolder> {

    private List<Predmet> mPredmets;
    private PredmetActionListener listener;

    public UserPredmetsAdapter() {
        mPredmets = new ArrayList<>();
    }

    @Override public UserPredmetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_user_predmet, parent, false);
        return new UserPredmetHolder(view);
    }

    @Override public void onBindViewHolder(UserPredmetHolder holder, int position) {
        final Predmet predmet = mPredmets.get(position);
        holder.tvPredmet.setText(predmet.getName());
        holder.btAddRemove.setText(predmet.isSelected()
                ? R.string.predmets_button_remove_text
                : R.string.predmets_button_add_text);
        holder.btAddRemove.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if(predmet.isSelected()) {
                    listener.onRemovePredmet(predmet);
                }
                else {
                    listener.onAddPredmet(predmet);
                }
            }
        });
    }

    @Override public int getItemCount() {
        return mPredmets.size();
    }

    public void setPredmets(List<Predmet> predmets) {
        mPredmets = predmets;
        notifyDataSetChanged();
    }

    public void setListener(PredmetActionListener listener) {
        this.listener = listener;
    }

    class UserPredmetHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_predmet) TextView tvPredmet;
        @BindView(R.id.bt_add_remove) Button btAddRemove;


        public UserPredmetHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface PredmetActionListener {
        void onAddPredmet(Predmet predmet);
        void onRemovePredmet(Predmet predmet);
    }
}
