package com.example.praveshsingh.chatapp.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.praveshsingh.chatapp.R;
import com.example.praveshsingh.chatapp.activities.ChatWindow;
import com.example.praveshsingh.chatapp.activities.FriendList_activity;
import com.example.praveshsingh.chatapp.activities.splashscreen;
import com.example.praveshsingh.chatapp.model_classes.FriendDetail;
import com.example.praveshsingh.chatapp.model_classes.RequestDetail;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.YourViewHolder> {
    Context context;
    List<FriendDetail> friendDetailList;

    public FriendAdapter(Context context, List<FriendDetail> friendDetailList) {
        this.context = context;
        this.friendDetailList = friendDetailList;
    }

    @Override
    public YourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_friend, parent, false);
        YourViewHolder yourViewHolder = new YourViewHolder(view);
        return yourViewHolder;
    }

    @Override
    public void onBindViewHolder(YourViewHolder holder, int position) {
        FriendDetail friendDetail = friendDetailList.get(position);
        String friendname = friendDetail.getFriendName();
        String friendid = String.valueOf(friendDetail.getFriendId());
        String friendemail = String.valueOf(friendDetail.getEmailId());
        holder.friendName.setText(friendname);
        holder.friendID.setText(friendid);
        holder.friendEmail.setText(friendemail);

    }

    @Override
    public int getItemCount() {
        return friendDetailList.size();
    }


    class YourViewHolder extends RecyclerView.ViewHolder {
        TextView friendName, friendID, friendEmail;

        public YourViewHolder(View itemView) {
            super(itemView);

            friendName = itemView.findViewById(R.id.friendListName);
            friendID = itemView.findViewById(R.id.friendListMemberID);
            friendEmail = itemView.findViewById(R.id.friendListEmailID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    FriendDetail f = friendDetailList.get(position);
                    String friendid = String.valueOf(f.getFriendId());
                    Intent i = new Intent(context, ChatWindow.class);
                    i.putExtra("friendid", friendid);
                    i.putExtra("friendname", f.getFriendName());
                    context.startActivity(i);
                }
            });
        }
    }

}
