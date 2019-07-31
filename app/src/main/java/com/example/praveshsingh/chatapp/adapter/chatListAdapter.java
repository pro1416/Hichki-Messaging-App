package com.example.praveshsingh.chatapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.praveshsingh.chatapp.R;
import com.example.praveshsingh.chatapp.activities.ChatWindow;
import com.example.praveshsingh.chatapp.model_classes.ChatList_model;

import java.util.List;

public class chatListAdapter extends RecyclerView.Adapter<chatListAdapter.MyViewHolder> {
    Context context;
    List<ChatList_model> chatList_modelList;

    public chatListAdapter(Context context, List<ChatList_model> chatList_modelList) {
        this.context = context;
        this.chatList_modelList = chatList_modelList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_chatlist, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ChatList_model model = chatList_modelList.get(i);
        String friendname = model.getFriendName();
        String friendid = model.getFriendID().toString();
        myViewHolder.friendName.setText(friendname);
        myViewHolder.friendID.setText(friendid);
        myViewHolder.profilepic.setText(model.getFriendName().substring(0, 1).toUpperCase());

    }

    @Override
    public int getItemCount() {
        return chatList_modelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView friendName, friendID, profilepic;
        Context context;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            context = itemView.getContext();
            friendName = itemView.findViewById(R.id.profilename);
            friendID = itemView.findViewById(R.id.profileid);
            profilepic = itemView.findViewById(R.id.profilepic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    ChatList_model f = chatList_modelList.get(position);
                    Intent i = new Intent(context, ChatWindow.class);
                    i.putExtra("friendid", f.getFriendID().toString());
                    i.putExtra("friendname", f.getFriendName());
                    Pair<View, String> p1 = Pair.create((View) profilepic, "shareddp");
                    Pair<View, String> p2 = Pair.create((View) friendName, "sharedname");
                    Pair<View, String> p3 = Pair.create((View) friendID, "sharedid");
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, p1, p2, p3);
                    context.startActivity(i, options.toBundle());


                }
            });
        }
    }
}
