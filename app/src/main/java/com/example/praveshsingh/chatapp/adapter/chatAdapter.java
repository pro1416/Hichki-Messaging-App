package com.example.praveshsingh.chatapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.praveshsingh.chatapp.R;
import com.example.praveshsingh.chatapp.model_classes.Messagez;
import com.example.praveshsingh.chatapp.model_classes.RecievedMessage;
import com.example.praveshsingh.chatapp.model_classes.SentMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.chatViewHolder> {
    Context context;
    List<Messagez> messagezList;


    public chatAdapter(Context context, List<Messagez> messagezList) {
        this.context = context;
        this.messagezList = messagezList;
    }


    @Override
    public chatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_message, parent, false);
        chatViewHolder chatViewHolders = new chatViewHolder(view);
        return chatViewHolders;
    }

    @Override
    public void onBindViewHolder(chatViewHolder holder, int position) {
        Collections.sort(messagezList);
        Messagez messagez = messagezList.get(position);
        if (messagez.getMsg_type().equals(Messagez.MSG_TYPE_RECIEVED)) {
            String recievecreated = messagez.getMsg_created().substring(11,16)+" "+messagez.getMsg_created().substring(0,10);
            holder.leftlayout.setVisibility(LinearLayout.VISIBLE);
            holder.lefttxtdetail.setText(recievecreated);
            holder.lefttxtdetail.setVisibility(LinearLayout.GONE);
            holder.lefttxtview.setText(messagez.getMessage_content());
            holder.rightlayout.setVisibility(LinearLayout.GONE);
        } else {
            String sentcreated = messagez.getMsg_created().substring(11,16)+" "+messagez.getMsg_created().substring(0,10);
            holder.leftlayout.setVisibility(LinearLayout.GONE);
            holder.righttxtview.setText(messagez.getMessage_content());
            holder.righttxtdetail.setText(sentcreated);
            holder.righttxtdetail.setVisibility(LinearLayout.GONE);
            holder.rightlayout.setVisibility(LinearLayout.VISIBLE);

        }


    }

    @Override
    public int getItemCount() {
        if (messagezList == null) {
            messagezList = new ArrayList<>();
        }
        return messagezList.size();
    }

    class chatViewHolder extends RecyclerView.ViewHolder {
        TextView lefttxtview, righttxtview,lefttxtdetail,righttxtdetail;
        LinearLayout leftlayout, rightlayout;

        public chatViewHolder(View itemView) {
            super(itemView);
            lefttxtdetail=itemView.findViewById(R.id.leftmsgdetail);
            righttxtdetail=itemView.findViewById(R.id.rightmsgdetail);
            lefttxtview = itemView.findViewById(R.id.lefttxtmessage);
            righttxtview = itemView.findViewById(R.id.righttxtmessage);
            leftlayout = itemView.findViewById(R.id.leftlinearlayout);
            rightlayout = itemView.findViewById(R.id.rightlinearlayout);
            lefttxtview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(lefttxtdetail.getVisibility()==LinearLayout.VISIBLE){
                        lefttxtdetail.setVisibility(LinearLayout.GONE);
                    }
                    else
                        lefttxtdetail.setVisibility(LinearLayout.VISIBLE);

                }
            });
            righttxtview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(righttxtdetail.getVisibility()==LinearLayout.VISIBLE){
                        righttxtdetail.setVisibility(LinearLayout.GONE);
                    }
                    else
                        righttxtdetail.setVisibility(LinearLayout.VISIBLE);

                }
            });

        }
    }
}
