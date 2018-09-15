package com.assigment.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.assigment.R;
import com.assigment.models.User;
import com.assigment.utility.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList = new ArrayList<>();
    private UserActions userActions;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        userActions = (UserActions) context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final User user = userList.get(position);

        if(Utils.getInstance().isStringValidated(user.getFirstName())){
            holder.firstName.setText(user.getFirstName().toString());
        }

        if(Utils.getInstance().isStringValidated(user.getLastName())){
            holder.lastName.setText(user.getLastName().toString());
        }

        if(Utils.getInstance().isStringValidated(user.getAvatar())){
            Glide.with(context)
                    .load(user.getAvatar().toString())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background)
                            .centerCrop())
                    .into(holder.profileImage);
        }else{
            Glide.with(context)
                    .load(R.drawable.ic_launcher_background)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background)
                            .centerCrop())
                    .into(holder.profileImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userActions.onUserClicked(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.first_name)
        TextView firstName;
        @BindView(R.id.last_name)
        TextView lastName;
        @BindView(R.id.profile_image)
        ImageView profileImage;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface UserActions{
        void onUserClicked(User user);
    }
}
