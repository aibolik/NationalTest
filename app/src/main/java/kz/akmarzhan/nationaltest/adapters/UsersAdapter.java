package kz.akmarzhan.nationaltest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.akmarzhan.nationaltest.R;
import kz.akmarzhan.nationaltest.models.User;
import kz.akmarzhan.nationaltest.utils.Utils;

/**
 * Created by aibol on 6/6/17.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {

    private String currentUserId;
    private List<User> mUsers;

    public UsersAdapter() {
        mUsers = new ArrayList<>();
    }

    @Override public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_user, parent, false);
        return new UserHolder(view);
    }

    @Override public void onBindViewHolder(UserHolder holder, int position) {
        User user = mUsers.get(position);
        if(user.getObjectId().equals(currentUserId)) {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.rating_highlighted));
        }
        holder.tvUserName.setText(user.getName());
        holder.tvUserLevel.setText(String.valueOf(Utils.getLevelByExpereience(user.getExp()).level));
        holder.tvUserExp.setText(holder.itemView.getContext().getString(R.string.rating_user_exp, user.getExp()));
    }

    @Override public int getItemCount() {
        return mUsers.size();
    }

    public void setUsers(List<User> mUsers) {
        this.mUsers = mUsers;
        notifyDataSetChanged();
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    class UserHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_user_level) TextView tvUserLevel;
        @BindView(R.id.tv_user_name) TextView tvUserName;
        @BindView(R.id.tv_user_exp) TextView tvUserExp;

        public UserHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
