package com.muhammed.citylabadmin.ui.adapter.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.muhammed.citylabadmin.R;
import com.muhammed.citylabadmin.data.model.login.UserData;
import com.muhammed.citylabadmin.data.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {


    private UserClickListener userClickListener;

    public UserAdapter(UserClickListener userClickListener) {
        this.userClickListener = userClickListener;
    }

    private  List<User> users = new ArrayList<>();


    public void addUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user = users.get(position);
        holder.name.setText(user.getName());
        holder.phone.setText(user.getPhoneNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickListener.openWhatsApp(user);
            }
        });


        holder.sendResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickListener.sendResultToUser(user);
            }
        });
    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserHolder extends RecyclerView.ViewHolder {

        AppCompatTextView name, phone, sendResult;
        ImageView whats_app_icon;


        public UserHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_name_item);
            phone = itemView.findViewById(R.id.user_phone_item);
            sendResult = itemView.findViewById(R.id.send_user_result_item);
            whats_app_icon = itemView.findViewById(R.id.open_whats);
        }
    }
}