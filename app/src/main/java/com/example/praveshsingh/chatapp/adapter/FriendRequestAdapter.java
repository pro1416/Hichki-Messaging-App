package com.example.praveshsingh.chatapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praveshsingh.chatapp.R;
import com.example.praveshsingh.chatapp.activities.Friend_RequestList_activity;
import com.example.praveshsingh.chatapp.model_classes.ActionOnRequest;
import com.example.praveshsingh.chatapp.model_classes.ActionOnRequestResponse;
import com.example.praveshsingh.chatapp.model_classes.RequestDetail;
import com.example.praveshsingh.chatapp.network.APIClient;
import com.example.praveshsingh.chatapp.network.APIInterface;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.MyViewHolder> {
    Context context;
    List<RequestDetail> requestDetailList;
    APIInterface apiInterface;

    public FriendRequestAdapter(Context context, List<RequestDetail> requestDetailList) {
        this.context = context;
        this.requestDetailList = requestDetailList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_request, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RequestDetail requestDetail = requestDetailList.get(position);
        String friendname = requestDetail.getFriendName();
        String friendid = String.valueOf(requestDetail.getFriendId());
        String friendemail = String.valueOf(requestDetail.getEmailId());
        holder.friendName.setText(friendname);
        holder.friendID.setText(friendid);
        holder.friendEmail.setText(friendemail);

    }

    @Override
    public int getItemCount() {
        return requestDetailList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView friendName, friendID, friendEmail;
        Button btnAccept, btnReject;

        public MyViewHolder(View itemView) {
            super(itemView);

            friendName = itemView.findViewById(R.id.txtFriendName);
            friendID = itemView.findViewById(R.id.txtFriendID);
            friendEmail = itemView.findViewById(R.id.txtFriendEmail);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnReject = itemView.findViewById(R.id.btnReject);
            apiInterface = APIClient.getRetroInstance().create(APIInterface.class);
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getAdapterPosition();
                    RequestDetail r = requestDetailList.get(position);
                    String friendmemberid = r.getApplicationFriendAssociationId().toString();
                    Call<ActionOnRequestResponse> call = apiInterface.getActionofRequest(new ActionOnRequest(friendmemberid, "Accept"));
                    call.enqueue(new Callback<ActionOnRequestResponse>() {
                        @Override
                        public void onResponse(Call<ActionOnRequestResponse> call, Response<ActionOnRequestResponse> response) {
                            ActionOnRequestResponse action = response.body();
                            if (action.getResponseData() == 1) {
                                Toast.makeText(context, "Request Accepted", Toast.LENGTH_SHORT).show();
                            }
                            requestDetailList.remove(position);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ActionOnRequestResponse> call, Throwable t) {
                            Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getAdapterPosition();
                    RequestDetail r = requestDetailList.get(position);
                    String friendmemberid = r.getApplicationFriendAssociationId().toString();
                    Call<ActionOnRequestResponse> call = apiInterface.getActionofRequest(new ActionOnRequest(friendmemberid, "Reject"));
                    call.enqueue(new Callback<ActionOnRequestResponse>() {
                        @Override
                        public void onResponse(Call<ActionOnRequestResponse> call, Response<ActionOnRequestResponse> response) {
                            ActionOnRequestResponse action = response.body();
                            if (action.getResponseData() == 2) {
                                Toast.makeText(context, "Request Rejected", Toast.LENGTH_SHORT).show();
                            }
                            requestDetailList.remove(position);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ActionOnRequestResponse> call, Throwable t) {
                            Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
                        }


                    });

                }
            });
        }
    }
}
